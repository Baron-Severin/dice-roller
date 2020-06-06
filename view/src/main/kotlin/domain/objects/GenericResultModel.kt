package domain.objects

import AttackDetails
import CheckInputs
import CheckResult

/**
 * TODO
 * used by view
 */
data class GenericResultModel(
    val inputs: CheckInputs?,
    val didSucceed: Boolean?,
    val successLevels: Int?,
    val didCrit: Boolean?,
    val attack: AttackDetails?
)

fun CheckResult.toDisplay(): GenericResultModel = when(this) {
    is CheckResult.None -> GenericResultModel(
        inputs = null,
        didSucceed = null,
        successLevels = null,
        didCrit = null,
        attack = null
    )
    is CheckResult.Simple -> GenericResultModel(
        inputs = this.inputs,
        didSucceed = this.didSucceed,
        successLevels = null,
        didCrit = this.didCrit,
        attack = null
    )
    is CheckResult.Dramatic -> GenericResultModel(
        inputs = this.inputs,
        didSucceed = this.didSucceed,
        successLevels = this.successLevels,
        didCrit = this.didCrit,
        attack = null
    )
    is CheckResult.Opposed.Partial -> GenericResultModel(
        inputs = this.inputs,
        didSucceed = null,
        successLevels = this.successLevels,
        didCrit = null,
        attack = null
    )
    is CheckResult.Opposed.Full -> GenericResultModel(
        inputs = null,
        didSucceed = this.didSucceed,
        successLevels = this.successLevels,
        didCrit = null,
        attack = null
    )
    is CheckResult.Combat.Partial -> GenericResultModel(
        inputs = this.inputs,
        didSucceed = null,
        successLevels = this.successLevels,
        didCrit = null,
        attack = null
    )
    is CheckResult.Combat.Full -> GenericResultModel(
        inputs = null,
        didSucceed = null,
        successLevels = null,
        didCrit = null,
        attack = this.attack
    )
}
