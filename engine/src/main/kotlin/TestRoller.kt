import kotlin.math.abs
import kotlin.math.max
import kotlin.random.Random

data class RollResult(val didSucceed: Boolean, val successLevels: Int, val didCrit: Boolean)

interface DiceRoll {
    /**
     * @return 1-100
     */
    fun roll(): Int
}

class RealDiceRoll : DiceRoll {
    override fun roll(): Int {
        return (Random.nextDouble() * 100).toInt()
    }
}

class TestRoller(private val diceRoll: DiceRoll) {
    fun dramaticTest(threshold: Int): RollResult {
        val roll = diceRoll.roll()
        val unmodifiedSuccessLevels = abs((roll / 10) - (threshold / 10))

        val (didSucceed, successLevels) = if (roll >= 96 && threshold >= 96) {
            false to max(1, unmodifiedSuccessLevels)
        } else if (roll <= 5 && threshold <= 5) {
            true to max(1, unmodifiedSuccessLevels)
        } else {
            (roll <= threshold) to unmodifiedSuccessLevels
        }

        val didCrit = roll == 100 ||
                (roll % 10) == (roll / 10)

        return RollResult(didSucceed, successLevels, didCrit)
    }
}
