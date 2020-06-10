import kotlin.math.abs
import kotlin.math.max
import kotlin.random.Random
import CheckResult.Dramatic
import CheckResult.Simple

enum class BodyLocation(val description: String) {
    HEAD("Head"),
    LEFT_ARM("Left Arm"),
    RIGHT_ARM("Right Arm"),
    BODY("Body"),
    LEFT_LEG("Left Leg"),
    RIGHT_LEG("Right Leg")
}

internal val realDiceRoll = {
    Random.nextInt(1, 101).also {
        log("Rolled $it")
    }
}

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
            successLevels = result.successLevels,
            didCrit = result.didCrit
        )
    }

    fun opposedCheckFull(actorThreshold: Int, receiverThreshold: Int): CheckResult.Opposed.Full {
        val actorResult = dramaticCheck(actorThreshold)
        val receiverResult = dramaticCheck(receiverThreshold)

        val successMargin = actorResult.successLevels - receiverResult.successLevels
        val actorWins = when {
            successMargin > 0 -> true
            successMargin < 0 -> false
            actorThreshold != receiverThreshold -> actorThreshold > receiverThreshold
            else -> {
                // Reroll to break the tie
                // TODO optional rule, null result
                log("opposedCheckFull: SL and threshold tie.  Rerolling")
                // TODO these should all be debug level logs
                listOf(
                    "Actor Roll/Threshold: ${actorResult.inputs.roll}/${actorThreshold}",
                    "Receiver Roll/Threshold: ${receiverResult.inputs.roll}/${receiverThreshold}",
                    "Actor SLs: ${actorResult.successLevels}",
                    "Receiver SLs: ${receiverResult.successLevels}",
                    "Success Margin: $successMargin"
                ).forEach { log("opposedCheckFull: Actor success levels: $it") }
                return opposedCheckFull(actorThreshold, receiverThreshold)
            }
        }

        return CheckResult.Opposed.Full(
            actorInputs = actorResult.inputs,
            receiverInputs = receiverResult.inputs,
            didSucceed = actorWins,
            netSuccessLevels = successMargin,
            actorDidCrit = didCrit(actorResult.inputs.roll),
            receiverDidCrit = didCrit(receiverResult.inputs.roll)
        )
    }

    fun combatCheckPartial(attackerThreshold: Int): CheckResult.Combat.Partial {
        val result = dramaticCheck(attackerThreshold)

        return CheckResult.Combat.Partial(
            inputs = result.inputs,
            successLevels = result.successLevels,
            didCrit = result.didCrit,
            critRoll = d100Roll()
        )
    }

    fun combatCheckFull(attackerThreshold: Int, defenderThreshold: Int): CheckResult.Combat.Full {
        val opposed = opposedCheckFull(attackerThreshold, defenderThreshold)

        fun getCrit(inputs: CheckInputs, isDefense: Boolean): CombatCrit? {
            if (!didCrit(inputs.roll)) {
                return null
            }

            return if (inputs.margin >= 0 && (isDefense || opposed.didSucceed)) {
                val location = hitLocation(inputs.roll)
                CombatCrit.Hit.get(location, d100Roll())
            } else if (inputs.margin < 0) {
                CombatCrit.Fumble.get(d100Roll())
            } else {
                null
            }
        }

        val attack = if (opposed.didSucceed) {
            AttackDetails.Hit(
                location = hitLocation(opposed.actorInputs.roll),
                netSuccessLevels = opposed.netSuccessLevels,
                crit = getCrit(opposed.actorInputs, false)
            )
        } else {
            AttackDetails.Miss(
                netSuccessLevels = opposed.netSuccessLevels,
                crit = getCrit(opposed.actorInputs, false)
            )
        }
        val defenderCrit = getCrit(opposed.receiverInputs, true)

        return CheckResult.Combat.Full(
            attackerInputs = opposed.actorInputs,
            defenderInputs = opposed.receiverInputs,
            attack = attack,
            defenderCrit = defenderCrit
        )
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

private fun log(text: String) {
    println("CheckRoller: $text")
}
