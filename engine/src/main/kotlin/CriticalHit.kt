
fun CriticalHit.Companion.get(location: BodyLocation, critRoll: Int): CriticalHit {
    fun List<CritData>.find(critRoll: Int) = this.first { critRoll in it.roll }

    val record = when(location) {
        BodyLocation.HEAD -> head.find(critRoll)
        BodyLocation.LEFT_ARM, BodyLocation.RIGHT_ARM -> arm.find(critRoll)
        BodyLocation.LEFT_LEG, BodyLocation.RIGHT_LEG -> leg.find(critRoll)
        BodyLocation.BODY -> body.find(critRoll)
    }

    return CriticalHit(critRoll, record.description, record.wounds, record.additionalEffects)
}

private data class CritData(
    val roll: IntRange,
    val description: String,
    val wounds: Int,
    val additionalEffects: String
)

private val head = listOf(
    CritData(
        roll = 1..10,
        description = "Dramatic Injury",
        wounds = 1,
        additionalEffects = "A fine wound across the forehead and cheek. Gain 1 Bleeding Condition. Once the wound is healed, the impressive scar it leaves provides a bonus of +1 SL to appropriate social Tests. You can only gain this benefit once."
    ),
    CritData(
        roll = 11..20,
        description = "Minor Cut",
        wounds = 1,
        additionalEffects = "The strike opens your cheek and blood flies. Gain 1 Bleeding Condition."
    ),
    CritData(
        roll = 21..25,
        description = "Poked Eye",
        wounds = 1,
        additionalEffects = "The blow glances across your eye socket. Gain 1 Blinded condition."
    ),
    CritData(
        roll = 26..30,
        description = "Ear Bash",
        wounds = 1,
        additionalEffects = "After a sickening impact, your ear is left ringing. Gain 1 Deafened Condition."
    ),
    CritData(
        roll = 31..35,
        description = "Rattling Blow ",
        wounds = 2,
        additionalEffects = "The blow floods your vision with flashing lights. Gain 1 Stunned Condition."
    ),
    CritData(
        roll = 36..40,
        description = "Black Eye",
        wounds = 2,
        additionalEffects = "A solid blow hits your eye, leaving tears and pain. Gain 2 Blinded Conditions."
    ),
    CritData(
        roll = 41..45,
        description = "Sliced Ear",
        wounds = 2,
        additionalEffects = "Your side of your head takes a hard blow, cutting deep into your ear. Gain 2 Deafened and 1 Bleeding Condition."
    ),
    CritData(
        roll = 46..50,
        description = "Struck Forehead",
        wounds = 2,
        additionalEffects = "A solid blow hits your forehead. Gain 2 Bleeding Conditions and a BlindedCondition that cannot be removed until all Bleeding Conditions are removed."
    ),
    CritData(
        roll = 51..55,
        description = "Fractured Jaw",
        wounds = 3,
        additionalEffects = "With a sickening crunch, pain fills your face as the blow fractures your jaw. Gain 2 Stunned Conditions. Suffer a Broken Bone (Minor) injury."
    ),
    CritData(
        roll = 56..60,
        description = "Major Eye Wound",
        wounds = 3,
        additionalEffects = "The blow cracks across your eye socket. Gain 1 Bleeding Condition. Also gain 1 Blinded Condition that cannot be removed until you receive Medical Attention. "
    ),
    CritData(
        roll = 61..65,
        description = "Major Ear Wound",
        wounds = 3,
        additionalEffects = "The blow strikes deep into one ear. Suffer a permanent –20 penalty on all Tests relating to hearing. If you suffer this result again, your hearing is permanently lost as the second ear falls quiet. Only magic can heal this."
    ),
    CritData(
        roll = 66..70,
        description = "Broken Nose",
        wounds = 3,
        additionalEffects = "A solid blow to the centre of your face causing blood to pour. Gain 2 BleedingConditions. Make a Challenging (+0) Endurance Test, or also gain a StunnedCondition. After this wound has healed, gain +1/–1 SL on social rolls, depending on context, unless Surgery is used to reset the nose. "
    ),
    CritData(
        roll = 71..75,
        description = "Broken Jaw",
        wounds = 4,
        additionalEffects = "The crack is sickening as the blow hits you under the chin, breaking your jaw. Gain 3 Stunned Conditions. Make a Challenging (+0) Endurance Test or gain an Unconscious Condition. Suffer a Broken Bone (Major) injury."
    ),
    CritData(
        roll = 76..80,
        description = "Concussive Blow",
        wounds = 4,
        additionalEffects = "Your brain rattles in your skull as blood spurts from your nose and ears. Take 1 Deafened, 2 Bleeding, and 1d10 Stunned Conditions. Gain a FatiguedCondition that lasts for 1d10 days. If you receive another Critical Wound to your head while suffering this Fatigued Condition, make an Average (+20) Endurance Test or also gain an Unconscious Condition."
    ),
    CritData(
        roll = 81..85,
        description = "Smashed Mouth",
        wounds = 4,
        additionalEffects = "With a sickening crunch, your mouth is suddenly filled with broken teeth and blood. Gain 2 Bleeding Conditions. Lose 1d10 teeth — Amputation (Easy)."
    ),
    CritData(
        roll = 86..90,
        description = "Mangled Ear",
        wounds = 4,
        additionalEffects = "Little is left of your ear as the blow tears it apart. You gain 3 Deafened and 2 Bleeding Conditions. Lose your ear —Amputation (Average)."
    ),
    CritData(
        roll = 91..93,
        description = "Devastated Eye",
        wounds = 5,
        additionalEffects = "A strike to your eye completely bursts it, causing extraordinary pain. Gain 3 Blinded, 2 Bleeding, and 1 Stunned Condition. Lose your eye — Amputation (Difficult)."
    ),
    CritData(
        roll = 94..96,
        description = "Disfiguring Blow",
        wounds = 5,
        additionalEffects = "The blow smashes your entire face, destroying your eye and nose in a cloud of blood. Gain 3 Bleeding, 3 Blinded and 2 Stunned Conditions. Lose your eye and nose — Amputation (Hard)."
    ),
    CritData(
        roll = 97..99,
        description = "Mangled Jaw",
        wounds = 5,
        additionalEffects = "The blow almost removes your jaw as it utterly destroys your tongue, sending teeth flying in a shower of blood. Gain 4 Bleeding and 3 Stunned Conditions. Make a Very Hard (–30) Endurance Test or gain an Unconscious Condition. Suffer a Broken Bone (Major) injury and lose your tongue and 1d10 teeth — Amputation (Hard). 00DecapitatedDeathYour head is entirely severed from your neck and soars through the air, landing 1d10 feet away in a random direction (see Scatter). Your body collapses, instantly dead."
    )
)

private val arm = listOf(
    CritData(
        roll = 1..10,
        description = "Jarred Arm",
        wounds = 1,
        additionalEffects = "Your arm is jarred in the attack. Drop whatever was held in that hand."
    ),
    CritData(
        roll = 11..20,
        description = "Minor cut",
        wounds = 1,
        additionalEffects = "Gain a Bleeding Condition as your upper arm is cut badly."
    ),
    CritData(
        roll = 21..25,
        description = "Sprain",
        wounds = 1,
        additionalEffects = "You sprain your arm, suffering a Torn Muscle (Minor) injury."
    ),
    CritData(
        roll = 26..30,
        description = "Badly Jarred Arm",
        wounds = 2,
        additionalEffects = "Your arm is badly jarred in the attack. Drop whatever was held in that hand, which is useless for 1d10 – Toughness Bonus Rounds (minimum 1). For this time, treat the hand as lost (see Amputated Parts). "
    ),
    CritData(
        roll = 31..35,
        description = "Torn Muscles",
        wounds = 2,
        additionalEffects = "The blow slams into your forearm. Gain a Bleeding Condition and a Torn Muscle (Minor) injury."
    ),
    CritData(
        roll = 36..40,
        description = "Bleeding Hand",
        wounds = 2,
        additionalEffects = "Your hand is cut badly, making your grip slippery. Take 1 Bleeding Condition. While suffering from that Bleeding Condition, make an Average (+20) Dexterity Test before taking any Action that requires something being held in that hand; if you fail, the item slips from your grip. "
    ),
    CritData(
        roll = 41..45,
        description = "Wrenched Arm",
        wounds = 2,
        additionalEffects = "Your arm is almost pulled from its socket. Drop whatever is held in the associated hand; the arm is useless for 1d10 Rounds (see Amputated Parts)."
    ),
    CritData(
        roll = 46..50,
        description = "Gaping Wound",
        wounds = 3,
        additionalEffects = "The blow opens a deep, gaping wound. Gain 2 Bleeding Conditions. Until you receive Surgery to stitch up the cut, any associated Arm Damage you receive will also inflict 1 Bleeding Condition as the wound reopens."
    ),
    CritData(
        roll = 51..55,
        description = "Clean Break",
        wounds = 3,
        additionalEffects = "An audible crack resounds as the blow strikes your arm. Drop whatever was held in the associated hand and gain a Broken Bone (Minor) injury. Pass a Difficult (–10) Endurance Test or gain a Stunned Condition."
    ),
    CritData(
        roll = 56..60,
        description = "Ruptured Ligament",
        wounds = 3,
        additionalEffects = "You immediately drop whatever was held in that hand. Suffer a Torn Muscle (Major) injury."
    ),
    CritData(
        roll = 61..65,
        description = "Deep Cut",
        wounds = 3,
        additionalEffects = "Gain 2 Bleeding Conditions as your arm is mangled. Gain 1 StunnedCondition and suffer a Torn Muscle (Minor) injury. Take a Hard (–20) Endurance Test or gain the Unconscious Condition. "
    ),
    CritData(
        roll = 66..70,
        description = "Damaged Artery",
        wounds = 4,
        additionalEffects = "Gain 4 Bleeding Conditions. Until you receive Surgery, every time you take Damage to this Arm Hit Location gain 2 Bleeding Conditions."
    ),
    CritData(
        roll = 71..75,
        description = "Crushed Elbow",
        wounds = 4,
        additionalEffects = "The blow crushes your elbow, splintering bone and cartilage. You immediately drop whatever was held in that hand and gain a Broken Bone (Major) injury."
    ),
    CritData(
        roll = 76..80,
        description = "Dislocated Shoulder",
        wounds = 4,
        additionalEffects = "Your arm is wrenched out of its socket. Pass a Hard (–20) Endurance Test or gain the Stunned and Prone Condition. Drop whatever is held in that hand: the arm is useless and counts as lost (see Amputated Part). Gain 1 StunnedCondition until you receive Medical Attention. After this initial Medical Attention, an Extended Average (+20) Heal Test needing 6 SL is required to reset the arm, at which point you regain its use. Tests made using this arm suffer a –10 penalty for 1d10 days. "
    ),
    CritData(
        roll = 81..85,
        description = "Severed Finger",
        wounds = 4,
        additionalEffects = "You gape in horror as a finger flies — Amputation (Average). Gain a Bleeding condition."
    ),
    CritData(
        roll = 86..90,
        description = "Cleft Hand",
        wounds = 5,
        additionalEffects = "Your hand splays open from the blow. Lose 1 finger —Amputation (Difficult). Gain 2 Bleeding and 1 Stunned Condition.  For every succeeding Round in which you don't receive Medical Attention, you lose another finger as the wound tears; if you run out of fingers, you lose the hand — Amputation (Difficult)."
    ),
    CritData(
        roll = 91..93,
        description = "Mauled Bicep",
        wounds = 5,
        additionalEffects = "The blow almost separates bicep and tendon from bone, leaving an ugly wound that sprays blood over you and your opponent. You automatically drop anything held in the associated hand and suffers a Torn Muscle (Major) injury and 2 Bleeding and 1 Stunned Condition."
    ),
    CritData(
        roll = 94..96,
        description = "Mangled Hand",
        wounds = 5,
        additionalEffects = "Your hand is left a mauled, bleeding mess. You lose your hand —Amputation (Hard). Gain 2 Bleeding Condition. Take a Hard (–20) Endurance Test or gain the Stunned and Prone Conditions. "
    ),
    CritData(
        roll = 97..99,
        description = "Sliced Tendons",
        wounds = 5,
        additionalEffects = "Your tendons are cut by the blow, leaving your arm hanging useless — Amputation (Very Hard). Gain 3 Bleeding, 1 Prone, and 1 Stunned Condition. Pass a Hard (–20) Endurance Test or gain the Unconscious Condition.00Brutal DismembermentDeathYour arm is severed, spraying arterial blood 1d10 feet in a random direction (see Scatter), before the blow follows through to your chest."
    )
)

private val body = listOf(
    CritData(
        roll = 1..10,
        description = "‘Tis But A Scratch!",
        wounds = 1,
        additionalEffects = "Gain 1 Bleeding Condition."
    ),
    CritData(
        roll = 11..20,
        description = "Gut Blow",
        wounds = 1,
        additionalEffects = "Gain 1 Stunned Condition. Pass an Easy (+40) Endurance Test, or vomit, gaining the Prone Condition."
    ),
    CritData(
        roll = 21..25,
        description = "Low Blow!",
        wounds = 1,
        additionalEffects = "Make a Hard (-20) Endurance Test or gain 3 Stunned Condition. "
    ),
    CritData(
        roll = 26..30,
        description = "Twisted Back",
        wounds = 1,
        additionalEffects = "Suffer a Torn Muscle (Minor) injury."
    ),
    CritData(
        roll = 31..35,
        description = "Winded",
        wounds = 2,
        additionalEffects = "Gain a Stunned Condition. Make an Average (+20) Endurance Test, or gain the Prone Condition. Movement is halved for 1d10 rounds as you get your breath back."
    ),
    CritData(
        roll = 36..40,
        description = "Bruised Ribs",
        wounds = 2,
        additionalEffects = "All Agility-based Tests suffer a –10 penalty for 1d10 days."
    ),
    CritData(
        roll = 41..45,
        description = "Wrenched Collar Bone",
        wounds = 2,
        additionalEffects = "Randomly select one arm. Drop whatever is held in that hand; the arm is useless for 1d10 rounds (see Amputated Parts)."
    ),
    CritData(
        roll = 46..50,
        description = "Ragged Wound",
        wounds = 2,
        additionalEffects = "Take 2 Bleeding Conditions."
    ),
    CritData(
        roll = 51..55,
        description = "Cracked Ribs",
        wounds = 3,
        additionalEffects = "The hit cracks one or more ribs. Gain a Stunned Condition. Gain a Broken Bone (Minor) injury."
    ),
    CritData(
        roll = 56..60,
        description = "Gaping Wound",
        wounds = 3,
        additionalEffects = "Take 3 Bleeding Conditions. Until you receive Surgery, any Wounds you receive to the Body Hit Location will inflict an additional Bleeding Condition as the cut reopens."
    ),
    CritData(
        roll = 61..65,
        description = "Painful Cut",
        wounds = 3,
        additionalEffects = "Gain 2 Bleeding Conditions and a Stunned Condition. Take a Hard (–20) Endurance Test or gain the Unconscious Condition as you black out from the pain. Unless you achieve 4+ SL, you also scream out in agony."
    ),
    CritData(
        roll = 66..70,
        description = "Arterial Damage",
        wounds = 3,
        additionalEffects = "Gain 4 Bleeding Conditions. Until you receive Surgery, every time you receive Damage to the Body Hit Location, gain 2 Bleeding Conditions."
    ),
    CritData(
        roll = 71..75,
        description = "Pulled Back",
        wounds = 4,
        additionalEffects = "Your back turns to white pain as you pull a muscle. Suffer a Torn Muscle (Major) injury."
    ),
    CritData(
        roll = 76..80,
        description = "Fractured Hip",
        wounds = 4,
        additionalEffects = "Gain a Stunned Condition. Take a Challenging (+0) Endurance Test or also gain the Prone Condition. Suffer a Broken Bone (Minor) injury."
    ),
    CritData(
        roll = 81..85,
        description = "Major Chest Wound",
        wounds = 4,
        additionalEffects = "You take a significant wound to your chest, flensing skin from muscle and sinew. Take 4 Bleeding Conditions. Until you receive Surgery, to stitch the wound together, any Wounds you receive to the Body Hit Location will also inflict 2 Bleeding Conditions as the tears reopen."
    ),
    CritData(
        roll = 86..90,
        description = "Gut Wound",
        wounds = 4,
        additionalEffects = "Contract a Festering Wound (see Disease and Infection) and gain 2 BleedingConditions."
    ),
    CritData(
        roll = 91..93,
        description = "Smashed Rib Cage",
        wounds = 5,
        additionalEffects = "Gain a Stunned Condition that can only be removed through Medical Attention, and suffer a Broken Bone (Major) injury."
    ),
    CritData(
        roll = 94..96,
        description = "Broken Collar Bone",
        wounds = 5,
        additionalEffects = "Gain the Unconscious Condition until you receive Medical Attention, and suffer a Broken Bone (Major) injury."
    ),
    CritData(
        roll = 97..99,
        description = "Internal bleeding",
        wounds = 5,
        additionalEffects = "Gain a Bleeding Condition that can only be removed through Surgery. Contract Blood Rot (see Disease and Infection). 00Torn ApartDeathYou are hacked in two. The top half lands in a random direction, and all characters within 2 yards are showered in blood."
    )
)

private val leg = listOf(
    CritData(
        roll = 1..10,
        description = "Stubbed Toe",
        wounds = 1,
        additionalEffects = "In the scuffle, you stub your toe. Pass a Routine (+20) Endurance Test or suffer –10 on Agility Tests until the end of the next turn. "
    ),
    CritData(
        roll = 11..20,
        description = "Twisted Ankle",
        wounds = 1,
        additionalEffects = "You go over your ankle, hurting it. Agility Tests suffer a –10  penalty for 1d10 rounds. "
    ),
    CritData(
        roll = 21..25,
        description = "Minor Cut",
        wounds = 1,
        additionalEffects = "Gain 1 Bleeding Condition. "
    ),
    CritData(
        roll = 26..30,
        description = "Lost Footing",
        wounds = 1,
        additionalEffects = "In the scuffle you lose your footing. Pass a Challenging (+0) Endurance Test or gain the Prone Condition. "
    ),
    CritData(
        roll = 31..35,
        description = "Thigh Strike",
        wounds = 2,
        additionalEffects = "A painful blow slams into your upper thigh. Gain a Bleeding Condition and take an Average (+20) Endurance Test or stumble, gaining the Prone Condition."
    ),
    CritData(
        roll = 36..40,
        description = "Sprained Ankle",
        wounds = 2,
        additionalEffects = "You sprain your ankle, giving you a Torn Muscle (Minor) injury."
    ),
    CritData(
        roll = 41..45,
        description = "Twisted Knee",
        wounds = 2,
        additionalEffects = "You twist your knee too far. Agility Tests suffer a –20 penalty for 1d10 rounds."
    ),
    CritData(
        roll = 46..50,
        description = "Badly Cut Toe",
        wounds = 2,
        additionalEffects = "Gain 1 Bleeding Condition. After the encounter, make a Challenging (+0) Endurance Test. If you fail, lose 1 toe —Amputation (Average)."
    ),
    CritData(
        roll = 51..55,
        description = "Bad Cut",
        wounds = 3,
        additionalEffects = "Gain 2 Bleeding conditions as a deep wound opens up your shin. Pass a Challenging (+0) Endurance Test or gain the Prone Condition."
    ),
    CritData(
        roll = 56..60,
        description = "Badly Twisted Knee",
        wounds = 3,
        additionalEffects = "You badly twist your knee trying to avoid your opponent. Gain a Torn Muscle (Major) injury."
    ),
    CritData(
        roll = 61..65,
        description = "Hacked Leg",
        wounds = 3,
        additionalEffects = "A cut bites down into the hip. Gain 1 Prone and 2 Bleeding Conditions, and suffer a Broken Bone (Minor) injury. Further, take a Hard (–20) EnduranceTest or also gain a Stunned condition from the pain."
    ),
    CritData(
        roll = 66..70,
        description = "Torn Thigh",
        wounds = 3,
        additionalEffects = "Gain 3 Bleeding Conditions as the weapon opens up your upper thigh. Pass a Challenging (+0) Endurance Test or gain the Prone Condition. Until you receive Surgery to stitch up the wound, each time you receive Damage to this Leg, also receive 1 Bleeding Condition. "
    ),
    CritData(
        roll = 71..75,
        description = "Ruptured Tendon",
        wounds = 4,
        additionalEffects = "Gain a Prone and Stunned Condition as one of your tendons tears badly. Pass a Hard (–20) Endurance Test or gain the Unconscious Condition. Your leg is useless (see Amputated Parts). Suffer a Torn Muscle (Major) injury."
    ),
    CritData(
        roll = 76..80,
        description = "Carved Shin",
        wounds = 4,
        additionalEffects = "The weapon drives clean through your leg by the knee, slicing into bone and through tendons. Gain a Stunned and Prone Condition. Further, suffer a Torn Muscle (Major) and Broken Bone (Minor) injury."
    ),
    CritData(
        roll = 81..85,
        description = "Broken Knee",
        wounds = 4,
        additionalEffects = "The blow hacks into your kneecap, shattering it into several pieces. You gain 1 Bleeding, 1 Prone, and 1 Stunned Condition, and a Broken Bone (Major)Injury as you fall to the ground, clutching your ruined leg."
    ),
    CritData(
        roll = 86..90,
        description = "Dislocated Knee",
        wounds = 4,
        additionalEffects = "Your knee is wrenched out of its socket. Gain the Prone Condition. Pass a Hard (–20) Endurance Test, or gain the Stunned Condition, which is not removed until you receive Medical Attention. After this initial Medical Attention, an Extended Average (+20) Heal Test needing 6 SL is required to reset the knee at which point you regain its use. Movement is halved, and Tests made using this leg suffer a –10 penalty for d10 days. "
    ),
    CritData(
        roll = 91..93,
        description = "Crushed Foot",
        wounds = 5,
        additionalEffects = "The blow crushes your foot. Make an Average (+20) Endurance Test; if you fail, gain the Prone condition and lose 1 toe, plus 1 additional toe for each SL below 0 — Amputation (Average). Gain 2 Bleeding Conditions. If you don't receive Surgery within 1d10 days, you will lose the foot entirely."
    ),
    CritData(
        roll = 94..96,
        description = "Severed Foot",
        wounds = 5,
        additionalEffects = "Your foot is severed at the ankle and lands 1d10 feet away in a random direction — Amputation (Hard) (see Scatter). You gain 3 Bleeding, 2 Stunned, and 1 Prone Condition."
    ),
    CritData(
        roll = 97..99,
        description = "Cut Tendon",
        wounds = 5,
        additionalEffects = "A major tendon at the back of your leg is cut, causing you to scream out in pain as your leg collapses. Gain 2 Bleeding, 2 Stunned, and 1 Prone Condition and look on in horror as your leg never works again — Amputation (Very Hard).00Shattered PelvisDeathThe blow shatters your pelvis, severing one leg then driving through to the next. You die instantly from traumatic shock."
    )
)
