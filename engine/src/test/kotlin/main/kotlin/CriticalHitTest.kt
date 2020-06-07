package main.kotlin

import get
import kotlin.test.Test
import kotlin.test.assertEquals

class CriticalHitTest {

    @Test
    fun head() {
        with(CombatCrit.Hit.get(BodyLocation.HEAD, 10)) {
            assertEquals(10, roll)
            assertEquals("Dramatic Injury", description)
            assertEquals(1, extraWounds)
        }
    }

    @Test
    fun left_arm() {
        with(CombatCrit.Hit.get(BodyLocation.LEFT_ARM, 31)) {
            assertEquals(31, roll)
            assertEquals("Torn Muscles", description)
            assertEquals(2, extraWounds)
        }
    }

    @Test
    fun right_arm() {
        with(CombatCrit.Hit.get(BodyLocation.RIGHT_ARM, 31)) {
            assertEquals(31, roll)
            assertEquals("Torn Muscles", description)
            assertEquals(2, extraWounds)
        }
    }

    @Test
    fun body() {
        with(CombatCrit.Hit.get(BodyLocation.BODY, 80)) {
            assertEquals(80, roll)
            assertEquals("Fractured Hip", description)
            assertEquals(4, extraWounds)
        }
    }

    @Test
    fun left_leg() {
        with(CombatCrit.Hit.get(BodyLocation.LEFT_LEG, 11)) {
            assertEquals(11, roll)
            assertEquals("Twisted Ankle", description)
            assertEquals(1, extraWounds)
        }
    }

    @Test
    fun right_leg() {
        with(CombatCrit.Hit.get(BodyLocation.RIGHT_LEG, 11)) {
            assertEquals(11, roll)
            assertEquals("Twisted Ankle", description)
            assertEquals(1, extraWounds)
        }
    }
}
