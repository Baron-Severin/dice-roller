
data class CheckInputs(val roll: Int, val threshold: Int, val margin: Int)

sealed class AttackDetails {
    data class Miss(
        val netSuccessLevels: Int,
        val didFumble: Boolean
    ) : AttackDetails() {
        override fun toString() = JSON.stringify(this)
    }
    data class Hit(
        val location: BodyLocation,
        val netSuccessLevels: Int,
        val crit: CriticalHit?
    ) : AttackDetails() {
        override fun toString() = JSON.stringify(this)
    }
}

sealed class CheckResult {
    object None : CheckResult() {
        override fun toString() = JSON.stringify(this)
    }
    data class Simple(
        val inputs: CheckInputs,
        val didSucceed: Boolean,
        val didCrit: Boolean
    ) : CheckResult() {
        override fun toString() = JSON.stringify(this)
    }
    data class Dramatic(
        val inputs: CheckInputs,
        val didSucceed: Boolean,
        val successLevels: Int,
        val didCrit: Boolean
    ) : CheckResult() {
        override fun toString() = JSON.stringify(this)
    }
    sealed class Opposed : CheckResult() {
        /**
         * Only one character's threshold is known
         */
        data class Partial(
            val inputs: CheckInputs,
            val successLevels: Int
        ) : Opposed() {
            override fun toString() = JSON.stringify(this)
        }

        data class Full(
            val activeInputs: CheckInputs,
            val passiveInputs: CheckInputs,
            val didSucceed: Boolean,
            val successLevels: Int
        // TODO active + passive crit
        ) : Opposed() {
            override fun toString() = JSON.stringify(this)
        }
    }
    sealed class Combat : CheckResult() {
        /**
         * Combat where only one participant's threshold is known
         */
        data class Partial(
            val inputs: CheckInputs,
            val successLevels: Int
        ) : Combat() {
            override fun toString() = JSON.stringify(this)
        }
        data class Full(
            val attackerInputs: CheckInputs,
            val defenderInputs: CheckInputs,
            val attack: AttackDetails
        ) : Combat() {
            override fun toString() = JSON.stringify(this)
        }
    }
}

data class CriticalHit(
    val roll: Int,
    val description: String,
    val extraWounds: Int,
    val additionalEffects: String
) {
    override fun toString() = JSON.stringify(this)
    companion object
}
