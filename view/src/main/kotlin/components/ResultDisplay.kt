@file:Suppress("FunctionName")

package components

import AttackDetails
import CheckResult
import GenericResultDisplay
import kotlinx.html.*
import kotlinx.html.dom.create
import org.w3c.dom.HTMLElement
import toDisplay
import kotlin.browser.document

const val CHECK_RESULT_ID = "CHECK_RESULT_ID"

fun CheckResultDisplay(result: CheckResult): HTMLElement {
    val extraBlock: (DIV.() -> Unit)? = when (result) {
        is CheckResult.Opposed.Full -> {
            { getOpposedResultDisplay(result) }
        }
        is CheckResult.Combat.Full -> {
            { getCombatResultDisplay(result) }
        }
        else -> null
    }

    return CheckResultDisplay(result.toDisplay(), extraBlock)
}

private fun CheckResultDisplay(
    result: GenericResultDisplay,
    addExtraBlock: (DIV.() -> Unit)? = null
): HTMLElement = document.create.div {
    val spanClass = colorClass(result.didSucceed)

    id = CHECK_RESULT_ID
    with (result) {
        didSucceed?.let { didSucceed ->
            p {
                +"Did succeed: "
                span(classes = spanClass) { +didSucceed.toString() }
            }
        }
        inputs?.let { inputs ->
            p {
                +"Roll/Difficulty: ${inputs.roll}/${inputs.threshold} "
                span(classes = spanClass) { +"(${inputs.margin})" }
            }
        }
        successLevels?.let { successLevels ->
            p {
                +"Success Levels: "
                span(classes = spanClass) { +successLevels.toString() }
            }
            if (didCrit == true) {
                p {
                    // TODO had to !! didSucceed.  Revisit model
                    val text = if (result.didSucceed!!) "Critical!" else "Fumble!"
                    span(classes = spanClass) { +text }
                }
            }
        }
        if (addExtraBlock != null) {
            addExtraBlock()
        }
    }
}

private fun DIV.getOpposedResultDisplay(
    result: CheckResult.Opposed.Full,
    block: (DIV.() -> Unit)? = null
) {
    div(classes = Constants.CSS.Class.CARD) {
        val activeSpanClass = colorClass(result.didSucceed)
        val passiveSpanClass = colorClass(!result.didSucceed)
        p {
            +"Active Roll/Skill Check: ${result.activeInputs.roll}/${result.activeInputs.threshold} "
            span(classes = activeSpanClass) { +"(${result.activeInputs.margin})" }
        }
        p {
            +"Passive Roll/Skill Check: ${result.passiveInputs.roll}/${result.passiveInputs.threshold} "
            span(classes = passiveSpanClass) { +"(${result.passiveInputs.margin})" }
        }
        block?.invoke(this)
    }
}

private fun DIV.getCombatResultDisplay(
    result: CheckResult.Combat.Full,
    block: (DIV.() -> Unit)? = null
) {
    div(classes = Constants.CSS.Class.CARD) {
        val attack = result.attack
        val attackerSpan = colorClass(attack is AttackDetails.Hit)
        val defenderSpan = colorClass(attack is AttackDetails.Miss)
        p {
            +"Attacker Roll/Skill Check: ${result.attackerInputs.roll}/${result.attackerInputs.threshold} "
            span(classes = attackerSpan) { +result.attackerInputs.margin.toString() }
        }
        p {
            +"Defender Roll/Skill Check: ${result.defenderInputs.roll}/${result.defenderInputs.threshold} "
            span(classes = defenderSpan) { +result.defenderInputs.margin.toString() }
        }

        if (attack is AttackDetails.Hit) {
            p {
                +"Hit Location: ${attack.location}"
            }
            p {
                +"Net Success Levels: "
                span(classes = attackerSpan) { +attack.netSuccessLevels.toString() }
            }
            val crit = attack.crit
            if (crit != null) {
                div(classes = Constants.CSS.Class.CARD) {
                    p {
                        span(classes = attackerSpan) { +"Critical hit!" }
                    }
                    p {
                        +"Crit Roll: ${crit.roll}"
                    }
                    p {
                        +crit.description
                    }
                    p {
                        +"Extra Damage: ${crit.extraWounds}"
                    }
                    p {
                        +crit.additionalEffects
                    }
                }
            }
        }
        block?.invoke(this)
    }
}

private fun colorClass(didSucceed: Boolean?): String = when (didSucceed) {
    null -> Constants.CSS.Class.NONE
    true -> Constants.CSS.Class.COLOR_SUCCESS
    false -> Constants.CSS.Class.COLOR_FAILURE
}
