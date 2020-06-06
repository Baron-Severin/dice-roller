
object Check {

    private val roller = CheckRoller(realDiceRoll)

    fun simple(threshold: Int): CheckResult.Simple {
        return roller.simpleCheck(threshold)
    }

    fun dramatic(threshold: Int): CheckResult.Dramatic {
        return roller.dramaticCheck(threshold)
    }
}
