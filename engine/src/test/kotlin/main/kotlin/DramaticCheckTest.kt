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

    private fun testRoll(
        threshold: Int,
        roll: Int,
        expectedSuccess: Boolean,
        expectedSuccessLevels: Int,
        expectedCrit: Boolean
    ) {
        nextRoll = roll
        val result = roller.dramaticCheck(threshold)

        assertEquals(expectedSuccess, result.didSucceed)
        assertEquals(expectedSuccessLevels, result.successLevels)
        assertEquals(expectedCrit, result.didCrit)
    }

    @Test
    fun WHEN_roll_is_barely_below_threshold_THEN_should_succeed() {
        testRoll(50, 49, true, 1, false)
    }

    @Test
    fun WHEN_roll_is_equal_to_threshold_THEN_should_succeed() {
        testRoll(50, 50, true, 0, false)
    }

    @Test
    fun WHEN_roll_is_same_tens_digit_but_higher_THEN_should_succeed() {
        testRoll(50, 51, false, 0, false)
    }

    @Test
    fun WHEN_roll_is_much_smaller_than_threshold_THEN_should_succeed() {
        testRoll(50, 6, true, 5, false)
    }

    @Test
    fun WHEN_roll_is_much_larger_than_threshold_THEN_should_succeed() {
        testRoll(50, 94, false, -4, false)
    }

    @Test
    fun WHEN_roll_is_barely_smaller_than_threshold_with_a_different_tens_digit_THEN_should_have_1_SL() {
        testRoll(71, 69, true, 1, false)
    }

    @Test
    fun WHEN_roll_is_1_to_5_THEN_should_succeed() {
        testRoll(-10, 5, true, 1, false)
        testRoll(-10, 4, true, 1, false)
        testRoll(-10, 3, true, 1, false)
        testRoll(-10, 2, true, 1, false)
        testRoll(-10, 1, true, 1, false)
    }

    @Test
    fun WHEN_roll_is_96_to_100_THEN_should_fail() {
        testRoll(99, 95, true, 0, false)
        testRoll(99, 96, false, -1, false)
        testRoll(99, 97, false, -1, false)
        testRoll(99, 98, false, -1, false)
        testRoll(99, 99, false, -1, true)
        testRoll(99, 100, false, -1, true)
    }

    @Test
    fun WHEN_threshold_is_super_high_WHEN_roll_is_normal_THEN_success_level_should_be_huge() {
        testRoll(200, 50, true, 15, false)
    }

    @Test
    fun GIVEN_threshold_is_super_low_WHEN_roll_is_normal_THEN_success_level_should_be_huge() {
        testRoll(-30, 50, false, -8, false)
    }
    
    @Test
    fun GIVEN_threshold_is_very_high_WHEN_roll_is_96_to_100_THEN_success_levels_should_be_high() {
        testRoll(120, 96, false, -3, false)
    }

    @Test
    fun GIVEN_threshold_is_very_low_WHEN_roll_is_1_to_5_THEN_success_levels_should_be_high() {
        testRoll(-50, 4, true, 5, false)
    }

    @Test
    fun threshold_should_be_based_on_the_tens_digit_and_not_raw_margin() {
        testRoll(19, 1, true, 1, false)
    }
}
