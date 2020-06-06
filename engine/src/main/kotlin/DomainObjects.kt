
data class CheckInputs(val roll: Int, val threshold: Int, val margin: Int)

sealed class AttackDetails {
    data class Miss(
        val netSuccessLevels: Int,
        val didFumble: Boolean
    ) : AttackDetails()
    data class Hit(
        val location: BodyLocation,
        val netSuccessLevels: Int,
        val crit: CriticalHit?
    ) : AttackDetails()
}

sealed class CheckResult {
    data class Simple(
        val inputs: CheckInputs,
        val didSucceed: Boolean,
        val didCrit: Boolean
    ) : CheckResult()
    data class Dramatic(
        val inputs: CheckInputs,
        val didSucceed: Boolean,
        val successLevels: Int,
        val didCrit: Boolean
    ) : CheckResult()
    sealed class Opposed : CheckResult() {
        /**
         * Only one character's threshold is known
         */
        data class Partial(
            val inputs: CheckInputs,
            val successLevels: Int
        ) : Opposed()
        data class Full(
            val activeInputs: CheckInputs,
            val passiveInputs: CheckInputs,
            val didSucceed: Boolean,
            val successLevels: Int
        // TODO active + passive crit
        ) : Opposed()
    }
    sealed class Combat : CheckResult() {
        /**
         * Combat where only one participant's threshold is known
         */
        data class Partial(
            val inputs: CheckInputs,
            val successLevels: Int
        ) : Combat()
        data class Full(
            val attackerInputs: CheckInputs,
            val defenderInputs: CheckInputs,
            val attack: AttackDetails
        ) : Combat()
    }
}

data class CriticalHit(
    val roll: Int,
    val description: String,
    val extraWounds: Int,
    val additionalEffects: String
) {
    companion object
}
