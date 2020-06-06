
data class CheckInputs(val roll: Int, val threshold: Int, val margin: Int)

sealed class AttackDetails {
    data class Hit(
        val location: BodyLocation,
        val netSuccessLevels: Int,
        val crit: CriticalHit?
    ) : AttackDetails() {
        override fun toString() = JSON.stringify(this)
    }
    data class Miss(
        val netSuccessLevels: Int,
        val didFumble: Boolean
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
        data class Partial( // TODO should crit/fumble
            val inputs: CheckInputs,
            val successLevels: Int
        ) : Opposed() {
            override fun toString() = JSON.stringify(this)
        }

        data class Full(
            val actorInputs: CheckInputs,
            val receiverInputs: CheckInputs,
            val didSucceed: Boolean,
            val successLevels: Int,
            val actorDidCrit: Boolean,
            val receiverDidCrit: Boolean
        ) : Opposed() {
            override fun toString() = JSON.stringify(this)
        }
    }
    sealed class Combat : CheckResult() {
        /**
         * Combat where only one participant's threshold is known
         */
        data class Partial( // TODO should crit/fumble
            val inputs: CheckInputs,
            val successLevels: Int
        ) : Combat() {
            override fun toString() = JSON.stringify(this)
        }
        data class Full( // TODO this does not handle fumbles
            val attackerInputs: CheckInputs,
            val defenderInputs: CheckInputs,
            val attack: AttackDetails,
            val defenderCrit: CriticalHit? // TODO bind to view // TODO is this the best way to represent this? in the model?
        ) : Combat() {
            override fun toString() = JSON.stringify(this)
        }
    }
}

// TODO add combat fumble
// TODO combat fumble table ("oops table") on 160
data class CriticalHit(
    val roll: Int,
    val description: String,
    val extraWounds: Int,
    val additionalEffects: String
) {
    override fun toString() = JSON.stringify(this)
    companion object
}
