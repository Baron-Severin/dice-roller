
/**
 * TODO
 * used by view
 */
data class GenericResultDisplay(
    val inputs: CheckInputs?,
    val didSucceed: Boolean?,
    val successLevels: Int?,
    val didCrit: Boolean?,
    val attack: AttackDetails?
)

fun CheckResult.toDisplay(): GenericResultDisplay = when(this) {
    is CheckResult.Simple -> GenericResultDisplay(
        inputs = this.inputs,
        didSucceed = this.didSucceed,
        successLevels = null,
        didCrit = this.didCrit,
        attack = null
    )
    is CheckResult.Dramatic -> GenericResultDisplay(
        inputs = this.inputs,
        didSucceed = this.didSucceed,
        successLevels = this.successLevels,
        didCrit = this.didCrit,
        attack = null
    )
    is CheckResult.Opposed.Partial -> GenericResultDisplay(
        inputs = this.inputs,
        didSucceed = null,
        successLevels = this.successLevels,
        didCrit = null,
        attack = null
    )
    is CheckResult.Opposed.Full -> GenericResultDisplay(
        inputs = null,
        didSucceed = this.didSucceed,
        successLevels = this.successLevels,
        didCrit = null,
        attack = null
    )
    is CheckResult.Combat.Partial -> GenericResultDisplay(
        inputs = this.inputs,
        didSucceed = null,
        successLevels = this.successLevels,
        didCrit = null,
        attack = null
    )
    is CheckResult.Combat.Full -> GenericResultDisplay(
        inputs = null,
        didSucceed = null,
        successLevels = null,
        didCrit = null,
        attack = this.attack
    )
}
