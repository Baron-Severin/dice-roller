
object Check {

    private val roller = CheckRoller(realDiceRoll)

    fun dramatic(threshold: Int): DramaticCheckResult {
        return roller.dramaticCheck(threshold)
    }
}
