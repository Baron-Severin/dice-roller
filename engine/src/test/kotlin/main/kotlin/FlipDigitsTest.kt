package main.kotlin

import TestRoller
import kotlin.test.Test
import kotlin.test.assertEquals

class FlipDigitsTest {

    private val roller = TestRoller {0}

    @Test
    fun double_digit_roll() {
        assertEquals(37, roller.flipDigits(73))
        assertEquals(46, roller.flipDigits(64))
    }

    @Test
    fun double_digit_roll_crit() {
        assertEquals(44, roller.flipDigits(44))
        assertEquals(99, roller.flipDigits(99))
    }

    @Test
    fun single_digit_roll() {
        assertEquals(40, roller.flipDigits(4))
    }

    @Test
    fun triple_digit_roll() {
        assertEquals(100, roller.flipDigits(100))
    }
}
