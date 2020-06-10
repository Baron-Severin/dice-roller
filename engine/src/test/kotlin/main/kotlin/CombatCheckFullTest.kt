@file:Suppress("TestFunctionName")

package main.kotlin

import AttackDetails
import CheckResult
import CheckRoller
import CombatCrit
import kotlin.test.*

class CombatCheckFullTest {

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
    fun chars_with_higher_success_levels_should_win() {
        setNextRolls(50, 50)
        var result = roller.combatCheckFull(70, 60)
        assertTrue(result.attack is AttackDetails.Hit)

        setNextRolls(50, 50)
        result = roller.combatCheckFull(60, 70)
        assertTrue(result.attack is AttackDetails.Miss)

        setNextRolls(50, 50)
        result = roller.combatCheckFull(30, 20)
        assertTrue(result.attack is AttackDetails.Hit)

        setNextRolls(50, 50)
        result = roller.combatCheckFull(20, 30)
        assertTrue(result.attack is AttackDetails.Miss)

        setNextRolls(40, 50)
        result = roller.combatCheckFull(20, 20)
        assertTrue(result.attack is AttackDetails.Hit)

        setNextRolls(50, 40)
        result = roller.combatCheckFull(20, 20)
        assertTrue(result.attack is AttackDetails.Miss)
    }

    @Test
    fun both_chars_can_crit_or_fumble_simultaneously() {
        setNextRolls(33, 55, 5, 5)
        val result = roller.combatCheckFull(45, 45)
        
        assertNotNull(result.attack.crit)
        assertNotNull(result.defenderCrit)
    }
    
    @Test 
    fun actor_can_hit_and_fumble_simultaneously() {
        setNextRolls(11, 99, 5, 5)
        val result = roller.combatCheckFull(50, 50)

        assertTrue { result.attack is AttackDetails.Hit }
        assertTrue { result.attack.crit is CombatCrit.Hit }
        assertTrue { result.defenderCrit is CombatCrit.Fumble }
    }
    
    @Test
    fun nondouble_rolls_should_not_crit() {
        fun CheckResult.Combat.Full.assertNoCrits() {
            assertNull(attack.crit)
            assertNull(defenderCrit)
        }

        setNextRolls(1, 2)
        var result = roller.combatCheckFull(100, 99)
        result.assertNoCrits()

        setNextRolls(10, 12)
        result = roller.combatCheckFull(100, 80)
        result.assertNoCrits()

        setNextRolls(25, 21)
        result = roller.combatCheckFull(74, 100)
        result.assertNoCrits()

        setNextRolls(97, 32)
        result = roller.combatCheckFull(12, 11)
        result.assertNoCrits()

        setNextRolls(51, 38)
        result = roller.combatCheckFull(33, 55)
        result.assertNoCrits()
    }
    
    @Test
    fun GIVEN_actor_did_miss_WHEN_actor_rolls_double_THEN_there_should_be_no_crit() {
        setNextRolls(99, 11, 5, 5)
        var result = roller.combatCheckFull(50, 50)

        assertTrue { result.attack is AttackDetails.Miss }
        assertTrue { result.attack.crit is CombatCrit.Fumble }
        assertTrue { result.defenderCrit is CombatCrit.Hit }

        setNextRolls(11, 99, 5, 5)
        result = roller.combatCheckFull(50, 50)

        assertTrue { result.attack is AttackDetails.Hit }
        assertTrue { result.attack.crit is CombatCrit.Hit }
        assertTrue { result.defenderCrit is CombatCrit.Fumble }
    }

    @Test
    fun both_chars_can_crit_simultaneously() {
        setNextRolls(11, 22, 5, 5)
        val result = roller.combatCheckFull(50, 50)

        assertTrue { result.attack is AttackDetails.Hit }
        assertTrue { result.attack.crit is CombatCrit.Hit }
        assertTrue { result.defenderCrit is CombatCrit.Hit }
    }
    
    @Test
    fun both_chars_can_fumble_simultaneously() {
        setNextRolls(88, 99, 5, 5)
        val result = roller.combatCheckFull(50, 50)

        assertTrue { result.attack is AttackDetails.Hit }
        assertTrue { result.attack.crit is CombatCrit.Fumble }
        assertTrue { result.defenderCrit is CombatCrit.Fumble }
    }
    
    @Test
    fun crit_roll_should_be_unrelated_to_hit_roll() {
        setNextRolls(11, 99, 3, 4)
        val result = roller.combatCheckFull(50, 50)

        assertEquals(11, result.attackerInputs.roll)
        assertEquals(3, (result.attack.crit as CombatCrit.Hit).roll)
        assertEquals(4, result.defenderCrit!!.roll)
    }

    @Test
    fun combat_rolls_should_follow_higher_SL_then_higher_threshold_then_reroll_precedence() {
        // Higher SL wins
        setNextRolls(90, 90)
        var result = roller.combatCheckFull(110, 150)
        assertTrue { result.attack is AttackDetails.Miss }
        
        // Higher threshold wins
        setNextRolls(40, 49)
        result = roller.combatCheckFull(50, 51)
        assertTrue { result.attack is AttackDetails.Miss }
        
        // Reroll
        setNextRolls(90, 90, 1, 50)
        result = roller.combatCheckFull(110, 110)
        assertTrue { result.attack is AttackDetails.Hit }
    }

    @Test
    fun WHEN_both_chars_auto_hit_THEN_higher_SL_should_win() {
        setNextRolls(5, 1)
        val result = roller.combatCheckFull(50, 20)

        assertTrue { result.attack is AttackDetails.Hit }
    }

    @Test
    fun WHEN_both_chars_auto_miss_THEN_higher_SL_should_win() {
        setNextRolls(98, 96)
        val result = roller.combatCheckFull(50, 20)

        assertTrue { result.attack is AttackDetails.Hit }
    }

    private fun setNextRolls(
        actor: Int,
        receiver: Int,
        rerollOne: Int? = null,
        rerollTwo: Int? = null
    ) {
        assertTrue("All declared rolls should be consumed") { nextRolls.isEmpty() }
        nextRolls.add(actor)
        nextRolls.add(receiver)
        rerollOne?.let { nextRolls.add(it) }
        rerollTwo?.let { nextRolls.add(it) }
    }
    
}
