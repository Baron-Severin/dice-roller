@file:Suppress("FunctionName")

package components

import AttackDetails
import CheckResult
import domain.objects.GenericResultModel
import kotlinx.html.*
import kotlinx.html.dom.create
import org.w3c.dom.HTMLElement
import domain.objects.toDisplay
import kotlin.browser.document

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
    result: GenericResultModel,
    addExtraBlock: (DIV.() -> Unit)? = null
): HTMLElement = document.create.div {
    val spanClass = colorClass(result.didSucceed)

    id = Constants.Id.CHECK_RESULT_CONTAINER
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
        }
        if (didCrit == true) {
            p {
                // TODO had to !! didSucceed.  Revisit model
                val text = if (result.didSucceed!!) "Critical!" else "Fumble!"
                span(classes = spanClass) { +text }
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
    div(classes = Constants.Css.Class.CARD) {
        val activeSpanClass = colorClass(result.didSucceed)
        val passiveSpanClass = colorClass(!result.didSucceed)
        p {
            +"Active Roll/Skill Check: ${result.actorInputs.roll}/${result.actorInputs.threshold} "
            span(classes = activeSpanClass) { +"(${result.actorInputs.margin})" }
        }
        p {
            +"Passive Roll/Skill Check: ${result.receiverInputs.roll}/${result.receiverInputs.threshold} "
            span(classes = passiveSpanClass) { +"(${result.receiverInputs.margin})" }
        }
        block?.invoke(this)
    }
}

private fun DIV.getCombatResultDisplay(
    result: CheckResult.Combat.Full,
    block: (DIV.() -> Unit)? = null
) {
    div(classes = Constants.Css.Class.CARD) {
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
                div(classes = Constants.Css.Class.CARD) {
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
    null -> Constants.Css.Class.NONE
    true -> Constants.Css.Class.COLOR_SUCCESS
    false -> Constants.Css.Class.COLOR_FAILURE
}
