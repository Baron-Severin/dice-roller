package domain.objects

enum class Pager(
    val displayName: String,
    val description: String
) {
    SIMPLE_CHECK(
        displayName = "Simple Test",
        description = "Basic pass/fail test. See pg. 149"
    ),
    DRAMATIC_CHECK(
        displayName = "Dramatic Test",
        description = "Most common test type.  Pass/fail with degrees of success.  See pg. 152"
    ),
    OPPOSED_CHECK(
        displayName = "Opposed Test",
        description = "Non-combat skill test against another character.  See pg. 153"
    ),
    COMBAT_CHECK(
        displayName = "Combat Test",
        description = "Opposed test to attack another character.  See pg 158"
    )
    // TODO extended check
}
