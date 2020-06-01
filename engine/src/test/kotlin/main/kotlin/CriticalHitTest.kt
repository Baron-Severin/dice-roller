package main.kotlin

import kotlin.test.Test
import kotlin.test.assertEquals

class CriticalHitTest {

    @Test
    fun head() {
        with(CriticalHit.get(BodyLocation.HEAD, 10)) {
            assertEquals("Dramatic Injury", description)
            assertEquals(1, wounds)
            assertEquals(BodyLocation.HEAD, location)
        }
    }

    @Test
    fun left_arm() {
        with(CriticalHit.get(BodyLocation.LEFT_ARM, 31)) {
            assertEquals("Torn Muscles", description)
            assertEquals(2, wounds)
            assertEquals(BodyLocation.LEFT_ARM, location)
        }
    }

    @Test
    fun right_arm() {
        with(CriticalHit.get(BodyLocation.RIGHT_ARM, 31)) {
            assertEquals("Torn Muscles", description)
            assertEquals(2, wounds)
            assertEquals(BodyLocation.RIGHT_ARM, location)
        }
    }

    @Test
    fun body() {
        with(CriticalHit.get(BodyLocation.BODY, 80)) {
            assertEquals("Fractured Hip", description)
            assertEquals(4, wounds)
            assertEquals(BodyLocation.BODY, location)
        }
    }

    @Test
    fun left_leg() {
        with(CriticalHit.get(BodyLocation.LEFT_LEG, 11)) {
            assertEquals("Twisted Ankle", description)
            assertEquals(1, wounds)
            assertEquals(BodyLocation.LEFT_LEG, location)
        }
    }

    @Test
    fun right_leg() {
        with(CriticalHit.get(BodyLocation.RIGHT_LEG, 11)) {
            assertEquals("Twisted Ankle", description)
            assertEquals(1, wounds)
            assertEquals(BodyLocation.RIGHT_LEG, location)
        }
    }
}
