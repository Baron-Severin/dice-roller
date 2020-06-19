@file:Suppress("FunctionName")

package components

import AttackDetails
import CheckResult
import CombatCrit
import NonCombatCrit
import domain.objects.GenericResultModel
import kotlinx.html.*
import kotlinx.html.dom.create
import org.w3c.dom.HTMLElement
import domain.objects.toDisplay
import kotlin.browser.document

fun CheckResultDisplay(result: CheckResult, isPrimaryDisplay: Boolean = true): HTMLElement {
    val extraBlock: (DIV.() -> Unit)? = when (result) {
        is CheckResult.Opposed.Full -> {
            { getOpposedResultDisplay(result) }
        }
        is CheckResult.Combat.Full -> {
            { getCombatResultDisplay(result) }
        }
        else -> null
    }

    return if (result is CheckResult.None) {
        EmptyCaseDisplay()
    } else {
        CheckResultDisplay(result.toDisplay(), isPrimaryDisplay, extraBlock)
    }
}

private fun EmptyCaseDisplay(): HTMLElement = document.create.div {
    id = Constants.Id.CHECK_RESULT_CONTAINER
    // TODO should there be some explanation text here?
}

private fun CheckResultDisplay(
    result: GenericResultModel,
    isPrimaryDisplay: Boolean = true,
    addExtraBlock: (DIV.() -> Unit)? = null
): HTMLElement = document.create.div(classes = Constants.Css.Class.CARD) {
    if (isPrimaryDisplay) id = Constants.Id.CHECK_RESULT_CONTAINER

    val spanClass = colorClass(result.didSucceed)
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
        if (critRoll != null) {
            p {
                +"Crit roll: "
                span(classes = colorClass(null)) { +result.critRoll.toString() }
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

        getNonCombatCrit(result.actorCrit, "Actor", actorSpanClass)

        p {
            +"Receiver Roll/Skill Check: ${result.receiverInputs.roll}/${result.receiverInputs.threshold} "
            span(classes = receiverSpanClass) { +"(${result.receiverInputs.margin})" }
        }

        getNonCombatCrit(result.receiverCrit, "Receiver", actorSpanClass)

        block?.invoke(this)
    }
}

private fun DIV.getNonCombatCrit(crit: NonCombatCrit?, character: String, spanClass: String) = when (crit) {
    is NonCombatCrit.Success -> span(classes = spanClass) { +"$character Critical!" }
    is NonCombatCrit.Fumble -> span(classes = spanClass) { +"$character Fumble!" }
    null -> null
}

private fun DIV.getCombatResultDisplay(
    result: CheckResult.Combat.Full,
    block: (DIV.() -> Unit)? = null
) {
    div(classes = Constants.Css.Class.CARD) {
        val attack = result.attack
        p {
            +"Attacker Roll/Skill Check: ${result.attackerInputs.roll}/${result.attackerInputs.threshold} "
            val attackerSlColor = colorClass(result.attackerInputs.margin >= 0)
            span(classes = attackerSlColor) { +result.attackerInputs.margin.toString() }
        }
        p {
            +"Defender Roll/Skill Check: ${result.defenderInputs.roll}/${result.defenderInputs.threshold} "
            val defenderSlColor = colorClass(result.defenderInputs.margin >= 0)
            span(classes = defenderSlColor) { +result.defenderInputs.margin.toString() }
        }

        div(classes = Constants.Css.Class.CARD) {
            val attackerColor = colorClass(attack is AttackDetails.Hit)
            p {
                span(classes = attackerColor) { +attack.description() }
            }
            p {
                +"Net Success Levels: "
                span(classes = attackerColor) { +attack.netSuccessLevels.toString() }
            }
            if (attack is AttackDetails.Hit) {
                p {
                    +"Hit Location: "
                    span(classes = Constants.Css.Class.COLOR_INDETERMINATE_SUCCESS) { +attack.location.description }
                }
            }
            getCombatCritCard(true, attack.crit)
        }
        getCombatCritCard(false, result.defenderCrit)
        block?.invoke(this)
    }
}

fun DIV.getCombatCritCard(isAttacker: Boolean, crit: CombatCrit?) {
    if (crit == null) return

    div(classes = Constants.Css.Class.CARD) {
        val (goodSpan, badSpan, participant) = if (isAttacker) {
            Triple(colorClass(true), colorClass(false), "Attacker")
        } else {
            Triple(colorClass(false), colorClass(true), "Defender")
        }

        p {
            val (text, spanClass) = when (crit) {
                is CombatCrit.Hit -> "$participant Critical Hit!" to goodSpan
                is CombatCrit.Fumble -> "$participant Fumble!" to badSpan
            }
            span(classes = spanClass) { +text }
        }
        p {
            +"Crit Roll: "
            span(colorClass(null)) { +crit.roll.toString() }
        }
        if (crit is CombatCrit.Hit) {
            p {
                +crit.description
            }
            p {
                +"Extra Damage: "
                val extraWounds = if (crit.extraWounds == Int.MAX_VALUE) "Death" else crit.extraWounds.toString()
                span(classes = goodSpan) { +extraWounds }
            }
        }
        p {
            +crit.additionalEffects
        }
    }
}

private fun colorClass(didSucceed: Boolean?): String = when (didSucceed) {
    null -> Constants.Css.Class.COLOR_INDETERMINATE_SUCCESS
    true -> Constants.Css.Class.COLOR_SUCCESS
    false -> Constants.Css.Class.COLOR_FAILURE
}
