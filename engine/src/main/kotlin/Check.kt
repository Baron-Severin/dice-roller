
object Check {

    private val roller = CheckRoller(realDiceRoll)

    fun dramaticCheck(threshold: Int): DramaticCheckResult {
        return roller.dramaticCheck(threshold)
    }
}
