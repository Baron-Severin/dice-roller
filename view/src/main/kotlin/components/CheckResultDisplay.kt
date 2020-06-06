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
    div {
        val actorSpanClass = colorClass(result.didSucceed)
        val receiverSpanClass = colorClass(!result.didSucceed)
        p {
            +"Actor Roll/Skill Check: ${result.actorInputs.roll}/${result.actorInputs.threshold} "
            span(classes = actorSpanClass) { +"(${result.actorInputs.margin})" }
        }
        if (result.actorDidCrit) {
            // TODO view probably shouldnt be calculating this
            val text = if (result.didSucceed) "Actor critical!" else "Actor fumble!"
            span(classes = actorSpanClass) { +text }
        }
        p {
            +"Receiver Roll/Skill Check: ${result.receiverInputs.roll}/${result.receiverInputs.threshold} "
            span(classes = receiverSpanClass) { +"(${result.receiverInputs.margin})" }
        }
        if (result.receiverDidCrit) {
            val text = if (result.didSucceed) "Receiver critical!" else "Receiver fumble!"
            span(classes = actorSpanClass) { +text }
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
                span(classes = attackerSpan) { +"Hit!" }
            }
            p {
                +"Hit Location: "
                span(classes = Constants.Css.Class.COLOR_INDETERMINATE_SUCCESS) { +attack.location.description }
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
                        +"Extra Damage: "
                        span(classes = attackerSpan) { +crit.extraWounds }
                    }
                    p {
                        +crit.additionalEffects
                    }
                }
            }
        } else {
            span(classes = attackerSpan) { +"Miss!" }
        }
        block?.invoke(this)
    }
}

private fun colorClass(didSucceed: Boolean?): String = when (didSucceed) {
    null -> Constants.Css.Class.COLOR_INDETERMINATE_SUCCESS
    true -> Constants.Css.Class.COLOR_SUCCESS
    false -> Constants.Css.Class.COLOR_FAILURE
}
