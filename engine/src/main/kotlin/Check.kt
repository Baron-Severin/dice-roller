
object Check {

    private val roller = CheckRoller(realDiceRoll)

    fun simple(threshold: Int): CheckResult.Simple {
        return roller.simpleCheck(threshold)
    }

    fun dramatic(threshold: Int): CheckResult.Dramatic {
        return roller.dramaticCheck(threshold)
    }

    fun opposed(actorThreshold: Int, receiverThreshold: Int?): CheckResult.Opposed {
        throw NotImplementedError()
    }

    fun combat(attackerThreshold: Int, defenderThreshold: Int?): CheckResult.Combat {
        throw NotImplementedError()
    }
}
