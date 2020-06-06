@file:Suppress("TestFunctionName")

package main.kotlin

import CheckRoller
import kotlin.test.*

// TODO there is a misunderstood rule here.  It should be possible to win while fumbling. Crit/fumbles should be based off your own SL, _not_ net SL

class OpposedCheckFullTest {
    private val fakeDiceRoll: () -> Int = {
        assertTrue("nextRolls is empty. Did the check reroll with too few rolls provided?") {
            nextRolls.isNotEmpty()
        }
        nextRolls.removeAt(0)
    }

    private lateinit var roller: CheckRoller
    private lateinit var nextRolls: MutableList<Int>

    @BeforeTest
    fun setup() {
        roller = CheckRoller(fakeDiceRoll)
        nextRolls = mutableListOf()
    }
    
    @AfterTest
    fun tearDown() {
        assertTrue("All declared rolls should be consumed") { nextRolls.isEmpty() }
    }
    
    @Test
    fun inputs_should_match_values_that_were_provided() {
        setNextRolls(11, 22)
        val result = roller.opposedCheckFull(33, 45)

        with (result) {
            assertEquals(11, actorInputs.roll)
            assertEquals(33, actorInputs.threshold)
            assertEquals(22, actorInputs.margin)

            assertEquals(22, receiverInputs.roll)
            assertEquals(45, receiverInputs.threshold)
            assertEquals(23, receiverInputs.margin)
        }
    }

    @Test
    fun attacker_dice_roll_should_happen_first() {
        // `nextRolls` depends on this behavior. Without it, other tests could pass while
        // behavior is broken.
        val actorRoll = 10
        val receiverRoll = 10
        setNextRolls(actorRoll, receiverRoll)
        
        val result = roller.opposedCheckFull(51, 50)
        
        assertEquals(actorRoll, result.actorInputs.roll)
        assertEquals(receiverRoll, result.receiverInputs.roll)
    }

    @Test
    fun popping_from_empty_list_should_throw() {
        nextRolls.clear()
        assertFailsWith(AssertionError::class) { fakeDiceRoll() }
    }

    @Test
    fun WHEN_actor_succeeds_and_receiver_fails_THEN_should_succeed() {
        setNextRolls(50, 50)
        val result = roller.opposedCheckFull(60, 40)

        with (result) {
            assertTrue(didSucceed)
            assertEquals(2, successLevels)
        }
    }

    @Test
    fun WHEN_actor_fails_and_receiver_succeeds_THEN_should_fail() {
        setNextRolls(50, 50)
        val result = roller.opposedCheckFull(40, 60)

        with (result) {
            assertFalse(didSucceed)
            assertEquals(-2, successLevels)
        }
    }

    @Test
    fun WHEN_both_fail_THEN_the_one_that_failed_less_should_win() {
        setNextRolls(90, 90)
        val actorWins = roller.opposedCheckFull(50, 30)

        with (actorWins) {
            assertTrue(didSucceed)
            assertEquals(2, successLevels)
        }

        setNextRolls(90, 90)
        val receiverWins = roller.opposedCheckFull(30, 50)

        with (receiverWins) {
            assertFalse(didSucceed)
            assertEquals(-2, successLevels)
        }
    }

    @Test
    fun WHEN_both_succeed_THEN_the_one_that_failed_less_should_win() {
        setNextRolls(20, 20)
        val actorWins = roller.opposedCheckFull(50, 30)

        with (actorWins) {
            assertTrue(didSucceed)
            assertEquals(2, successLevels)
        }

        setNextRolls(20, 20)
        val receiverWins = roller.opposedCheckFull(30, 50)

        with (receiverWins) {
            assertFalse(didSucceed)
            assertEquals(-2, successLevels)
        }
    }
    
    @Test
    fun success_level_should_be_based_on_tens_digit_and_not_raw_difference() {
        setNextRolls(49, 50)
        val result = roller.opposedCheckFull(59, 69)
        
        with (result) {
            assertFalse(didSucceed)
            assertEquals(0, successLevels)
        }
    }
    
    @Test
    fun both_parties_should_always_crit_on_doubles() {
        setNextRolls(33, 22)
        var result = roller.opposedCheckFull(50, 50)

        with (result) {
            assertTrue(actorDidCrit)
            assertTrue(receiverDidCrit)
        }

        setNextRolls(33, 22)
        result = roller.opposedCheckFull(20, 20)

        with (result) {
            assertTrue(actorDidCrit)
            assertTrue(receiverDidCrit)
        }

        setNextRolls(33, 54)
        result = roller.opposedCheckFull(50, 50)

        with (result) {
            assertTrue(actorDidCrit)
            assertFalse(receiverDidCrit)
        }

        setNextRolls(33, 24)
        result = roller.opposedCheckFull(20, 20)

        with (result) {
            assertTrue(actorDidCrit)
            assertFalse(receiverDidCrit)
        }

        setNextRolls(33, 23)
        result = roller.opposedCheckFull(50, 50)

        with (result) {
            assertTrue(actorDidCrit)
            assertFalse(receiverDidCrit)
        }

        setNextRolls(11, 35)
        result = roller.opposedCheckFull(20, 20)

        with (result) {
            assertTrue(actorDidCrit)
            assertFalse(receiverDidCrit)
        }
    }
    
    @Test
    fun GIVEN_success_levels_are_tied_WHEN_thresholds_are_different_THEN_thresholds_should_break_tie() {
        setNextRolls(11, 51)
        val result = roller.opposedCheckFull(21, 61)
        
        with (result) {
            assertFalse { didSucceed }
            assertEquals(0, successLevels)
        }
    }

    @Test
    fun GIVEN_success_levels__and_thresholds_are_tied_THEN_reroll_should_break_tie() {
        setNextRolls(11, 11, 11, 99)
        val result = roller.opposedCheckFull(21, 21)

        with (result) {
            assertTrue { didSucceed }
            assertEquals(8, successLevels)
        }
    }

    private fun setNextRolls(
        actor: Int,
        receiver: Int,
        actorReroll: Int? = null,
        receiverReroll: Int? = null
    ) {
        assertTrue("All declared rolls should be consumed") { nextRolls.isEmpty() }
        nextRolls.add(actor)
        nextRolls.add(receiver)
        actorReroll?.let { nextRolls.add(it) }
        receiverReroll?.let { nextRolls.add(it) }
    }
}
