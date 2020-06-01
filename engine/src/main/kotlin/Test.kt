
object Test {

    private val roller = TestRoller(realDiceRoll)

    fun dramaticTest(threshold: Int): DramaticTestResult {
        return roller.dramaticTest(threshold)
    }
}
