
object Check {

    private val roller = CheckRoller(realDiceRoll)

    fun dramatic(threshold: Int): CheckResult.Dramatic {
        return roller.dramaticCheck(threshold)
    }
}
