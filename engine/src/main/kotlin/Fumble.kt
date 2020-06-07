
fun CombatCrit.Fumble.Companion.get(critRoll: Int): CombatCrit.Fumble {
    val data = oopsTable.first { critRoll in it.roll }
    return CombatCrit.Fumble(critRoll, data.additionalEffects)
}

private data class FumbleData(
    val roll: IntRange,
    val additionalEffects: String
)

private val oopsTable = listOf(
    FumbleData(
        roll = 1..20,
        additionalEffects = "You catch a part of your anatomy (we recommend you play this for laughs) — lose 1 Wound, ignoring Toughness Bonus or Armour Points."
    ),
    FumbleData(
        roll = 21..40,
        additionalEffects = "Your melee weapon jars badly, or ranged weapon malfunctions or slightly breaks – your weapon suffers 1 Damage. Next round, you will act last regardless of Initiative order, Talents, or special rules as you recover (see page 156)."
    ),
    FumbleData(
        roll = 41..60,
        additionalEffects = "Your manoeuvre was misjudged, leaving you out of position, or you lose grip of a ranged weapon. Next round, your Action suffers a penalty of –10."
    ),
    FumbleData(
        roll = 61..70,
        additionalEffects = "You stumble badly, finding it hard to right yourself. Lose your next Move."
    ),
    FumbleData(
        roll = 71..80,
        additionalEffects = "You mishandle your weapon, or you drop your ammunition. Miss your next Action."
    ),
    FumbleData(
        roll = 81..90,
        additionalEffects = "You overextend yourself or stumble and twist your ankle. Suffer a Torn Muscle (Minor) injury (see page 179). This counts as a Critical Wound."
    ),
    FumbleData(
        roll = 91..100,
        additionalEffects = "You completely mess up, hitting 1 random ally in range using your rolled units die to determine the SL of the hit. If that’s not possible, you somehow hit yourself in the face and gain a Stunned Condition (see page 169)."
    )
)
