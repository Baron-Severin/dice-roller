import kotlin.test.Test
import kotlin.test.assertEquals

class ViewUpdaterTest {

    class Tree(
        val value: Int,
        val left: Tree? = null,
        val right: Tree? = null
    )

    @Test
    fun case1() {
        val items =
            Tree(1,
                Tree(2,
                    Tree(3), Tree(4)
                ),
                Tree(5,
                    Tree(6)
                )
            ) to
                    Tree(1,
                        Tree(2,
                            Tree(3), Tree(4)
                        ),
                        Tree(5,
                            Tree(7)
                        )
                    )
        test(items, true)
    }

    @Test
    fun case2() {
        val items =
            Tree(1,
                Tree(2,
                    Tree(3), Tree(4)
                ),
                Tree(5,
                    Tree(6)
                )
            ) to
            Tree(1,
                Tree(2,
                    Tree(3), Tree(4)
                ),
                Tree(5)
            )
        test(items, true)
    }

    @Test
    fun case3() {
        val items =
            Tree(1,
                Tree(2,
                    Tree(3), Tree(4)
                ),
                Tree(5)
            ) to
            Tree(1,
                Tree(2,
                    Tree(3), Tree(4)
                ),
                Tree(5,
                    Tree(6)
                )
            )
        test(items, true)
    }

    @Test
    fun case4() {
        val items = Tree(1) to Tree(2)
        test(items, true)
    }

    @Test
    fun case5() {
        val items = Tree(1) to Tree(1)
        test(items, false)
    }

    @Test
    fun case6() {
        val items =
            Tree(1,
                Tree(2,
                    Tree(3)
                ),
                Tree(4,
                    Tree(5)
                )
            ) to
            Tree(1,
                Tree(4,
                    Tree(5)
                ),
                Tree(2,
                    Tree(3)
                )
            )
        test(items, true)
    }

    private fun test(items: Pair<Tree, Tree>, expectReplaced: Boolean) {
        var actualReplaced = false

        ViewUpdater.replaceIfNew(
            items = listOf(items),
            toChildren = { sequenceOf(this.left, this.right).filterNotNull() },
            equals = { this.value == it.value },
            replaceWith = { actualReplaced = true }
        )

        assertEquals(expectReplaced, actualReplaced)
    }
}
