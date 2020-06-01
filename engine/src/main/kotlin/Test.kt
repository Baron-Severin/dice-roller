
object Test {

    private val roller = TestRoller(realDiceRoll)

    fun dramaticTest(threshold: Int): RollResult {
        return roller.dramaticTest(threshold)
    }
}
