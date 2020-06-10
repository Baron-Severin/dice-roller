
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
            val successLevels: Int,
            val crit: NonCombatCrit?
        ) : Opposed() {
            override fun toString() = JSON.stringify(this)
        }

        data class Full(
            val actorInputs: CheckInputs,
            val receiverInputs: CheckInputs,
            val didSucceed: Boolean,
            val netSuccessLevels: Int,
            val actorCrit: NonCombatCrit?,
            val receiverCrit: NonCombatCrit?
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
            val successLevels: Int,
            val didCrit: Boolean,
            val critRoll: Int
        ) : Combat() {
            override fun toString() = JSON.stringify(this)
        }
        data class Full(
            val attackerInputs: CheckInputs,
            val defenderInputs: CheckInputs,
            val attack: AttackDetails,
            val defenderCrit: CombatCrit?
        ) : Combat() {
            override fun toString() = JSON.stringify(this)
        }
    }
}

data class CheckInputs(val roll: Int, val threshold: Int, val margin: Int)

sealed class AttackDetails {
    abstract val crit: CombatCrit?
    abstract val netSuccessLevels: Int

    abstract fun description() : String

    data class Hit(
        val location: BodyLocation,
        override val netSuccessLevels: Int,
        override val crit: CombatCrit?
    ) : AttackDetails() {
        override fun toString() = JSON.stringify(this)

        override fun description() = "Hit!"
    }
    data class Miss(
        override val netSuccessLevels: Int,
        override val crit: CombatCrit?
    ) : AttackDetails() {
        override fun toString() = JSON.stringify(this)

        override fun description() = "Miss!"
    }
}

sealed class NonCombatCrit {
    object Success : NonCombatCrit()
    object Fumble : NonCombatCrit()
}

sealed class CombatCrit {
    abstract val roll: Int
    abstract val additionalEffects: String

    data class Hit(
        override val roll: Int,
        val description: String,
        val extraWounds: Int,
        override val additionalEffects: String
    ) : CombatCrit() {
        override fun toString() = JSON.stringify(this)
        companion object
    }
    data class Fumble(
        override val roll: Int,
        override val additionalEffects: String
    ) : CombatCrit() {
        override fun toString() = JSON.stringify(this)
        companion object
    }
}
