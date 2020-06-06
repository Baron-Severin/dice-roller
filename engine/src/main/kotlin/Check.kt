
object Check {

    private val roller = CheckRoller(realDiceRoll)

    fun simple(threshold: Int): CheckResult.Simple {
        return roller.simpleCheck(threshold)
    }

    fun dramatic(threshold: Int): CheckResult.Dramatic {
        return roller.dramaticCheck(threshold)
    }

    fun opposed(actorThreshold: Int, receiverThreshold: Int?): CheckResult.Opposed {
        return if (receiverThreshold != null) {
            roller.opposedCheckFull(actorThreshold, receiverThreshold)
        } else {
            roller.opposedCheckPartial(actorThreshold)
        }
    }

    fun combat(attackerThreshold: Int, defenderThreshold: Int?): CheckResult.Combat {
        return if (defenderThreshold != null) {
            roller.combatCheckFull(attackerThreshold, defenderThreshold)
        } else {
            roller.combatCheckPartial(attackerThreshold)
        }
    }
}
