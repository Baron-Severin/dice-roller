package main.kotlin

import CheckRoller
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DramaticCheckTest {

    private var nextRoll = 0
    private val fakeDiceRoll: () -> Int = { nextRoll }

    private lateinit var roller: CheckRoller

    @BeforeTest
    fun setup() {
        roller = CheckRoller(fakeDiceRoll)
    }

    private fun testSimpleRoll(
        threshold: Int,
        roll: Int,
        expectedSuccess: Boolean,
        expectedSuccessLevels: Int,
        expectedCrit: Boolean
    ) {
        nextRoll = roll
        val result = roller.dramaticCheck(threshold)

        println("result: $result")

        assertEquals(expectedSuccess, result.didSucceed)
        assertEquals(expectedSuccessLevels, result.successLevels)
        assertEquals(expectedCrit, result.didCrit)
    }

    @Test
    fun WHEN_roll_is_barely_below_threshold_THEN_should_succeed() {
        testSimpleRoll(50, 49, true, 1, false)
    }

    @Test
    fun WHEN_roll_is_equal_to_threshold_THEN_should_succeed() {
        testSimpleRoll(50, 50, true, 0, false)
    }

    @Test
    fun WHEN_roll_is_same_tens_digit_but_higher_THEN_should_succeed() {
        testSimpleRoll(50, 51, false, 0, false)
    }

    @Test
    fun WHEN_roll_is_much_smaller_than_threshold_THEN_should_succeed() {
        testSimpleRoll(50, 6, true, 5, false)
    }

    @Test
    fun WHEN_roll_is_much_larger_than_threshold_THEN_should_succeed() {
        testSimpleRoll(50, 94, false, -4, false)
    }

    @Test
    fun WHEN_roll_is_barely_smaller_than_threshold_with_a_different_tens_digit_THEN_should_have_1_SL() {
        testSimpleRoll(71, 69, true, 1, false)
    }

    @Test
    fun WHEN_roll_is_1_to_5_THEN_should_succeed() {
        testSimpleRoll(-10, 5, true, 1, false)
        testSimpleRoll(-10, 4, true, 1, false)
        testSimpleRoll(-10, 3, true, 1, false)
        testSimpleRoll(-10, 2, true, 1, false)
        testSimpleRoll(-10, 1, true, 1, false)
    }

    @Test
    fun WHEN_roll_is_96_to_100_THEN_should_fail() {
        testSimpleRoll(99, 95, true, 0, false)
        testSimpleRoll(99, 96, false, -1, false)
        testSimpleRoll(99, 97, false, -1, false)
        testSimpleRoll(99, 98, false, -1, false)
        testSimpleRoll(99, 99, false, -1, true)
        testSimpleRoll(99, 100, false, -1, true)
    }

    @Test
    fun WHEN_threshold_is_super_high_WHEN_roll_is_normal_THEN_success_level_should_be_huge() {
        testSimpleRoll(200, 50, true, 15, false)
    }

    @Test
    fun GIVEN_threshold_is_super_low_WHEN_roll_is_normal_THEN_success_level_should_be_huge() {
        testSimpleRoll(-30, 50, false, -8, false)
    }
    
    @Test
    fun GIVEN_threshold_is_very_high_WHEN_roll_is_96_to_100_THEN_success_levels_should_be_high() {
        testSimpleRoll(120, 96, false, -3, false)
    }

    @Test
    fun GIVEN_threshold_is_very_low_WHEN_roll_is_1_to_5_THEN_success_levels_should_be_high() {
        testSimpleRoll(-50, 4, true, 5, false)
    }
}
