import kotlin.math.abs
import kotlin.math.max
import kotlin.random.Random
import CheckResult.Dramatic
import CheckResult.Simple

enum class BodyLocation { HEAD, LEFT_ARM, RIGHT_ARM, BODY, LEFT_LEG, RIGHT_LEG }

internal val realDiceRoll = { (Random.nextDouble() * 100).toInt() }

@VisibleForTesting(otherwise = VisibleForTesting.INTERNAL)
class CheckRoller(private val d100Roll: () -> Int) {

    fun simpleCheck(threshold: Int): Simple {
        val dramatic = dramaticCheck(threshold)
        return Simple(dramatic.inputs, dramatic.didSucceed, dramatic.didCrit)
    }

    fun dramaticCheck(threshold: Int): Dramatic {
        val roll = d100Roll()
        val unmodifiedSuccessLevels = abs((roll / 10) - (threshold / 10))

        val (didSucceed, successLevels) = if (roll >= 96 && threshold >= 96) {
            false to max(1, unmodifiedSuccessLevels)
        } else if (roll <= 5 && threshold <= 5) {
            true to max(1, unmodifiedSuccessLevels)
        } else {
            (roll <= threshold) to unmodifiedSuccessLevels
        }
        val modifiedSL = if (didSucceed) successLevels else -successLevels

        val didCrit = didCrit(roll)

        return Dramatic(CheckInputs(roll, threshold, threshold - roll), didSucceed, modifiedSL, didCrit)
    }

    fun opposedCheckPartial(actorThreshold: Int): CheckResult.Opposed.Partial {
        val result = dramaticCheck(actorThreshold)

        return CheckResult.Opposed.Partial(
            inputs = result.inputs,
            successLevels = result.successLevels
        )
    }

    fun opposedCheckFull(actorThreshold: Int, receiverThreshold: Int): CheckResult.Opposed.Full {
        val actorResult = dramaticCheck(actorThreshold)
        val receiverResult = dramaticCheck(receiverThreshold)

        val successMargin = actorResult.successLevels - receiverResult.successLevels
        val actorWins = if (successMargin > 0) {
            true
        } else if (successMargin < 0) {
            false
        } else if (actorThreshold != receiverThreshold) {
            actorThreshold > receiverThreshold
        } else {
            // Reroll to break the tie
            // TODO optional rule, null result
            return opposedCheckFull(actorThreshold, receiverThreshold)
        }

        return CheckResult.Opposed.Full(
            actorInputs = actorResult.inputs,
            receiverInputs = receiverResult.inputs,
            didSucceed = actorWins,
            successLevels = successMargin,
            actorDidCrit = didCrit(actorResult.inputs.roll),
            receiverDidCrit = didCrit(receiverResult.inputs.roll)
        )
    }

    fun combatCheckPartial(actorThreshold: Int): CheckResult.Combat.Partial {
        throw NotImplementedError()
    }

    fun combatCheckFull(actorThreshold: Int, receiverThreshold: Int): CheckResult.Combat.Full {
        throw NotImplementedError()
    }

    private fun didCrit(roll: Int): Boolean = roll == 100 ||
            (roll % 10) == (roll / 10)

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun flipDigits(roll: Int): Int = when (roll) {
        100 -> 100
        else -> (roll / 10) + ((roll % 10) * 10)
    }

    private fun hitLocation(roll: Int): BodyLocation {
        val flippedRoll = flipDigits(roll)
        return when (flippedRoll) {
            in 1..9 -> BodyLocation.HEAD
            in 10..24 -> BodyLocation.LEFT_ARM
            in 25..44 -> BodyLocation.RIGHT_ARM
            in 45..79 -> BodyLocation.BODY
            in 80..89 -> BodyLocation.LEFT_LEG
            in 90..100 -> BodyLocation.RIGHT_LEG
            else -> throw IllegalArgumentException("Roll must be within 1-100")
        }
    }
}
