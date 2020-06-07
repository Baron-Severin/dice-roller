import org.w3c.dom.*

object ViewUpdater {

    fun replaceIfNew(elements: List<Pair<HTMLElement, HTMLElement>>) {
        // Perf on this will be terrible. If we start to see perf issues, look here first
        replaceIfNew<Element>(
            items = elements,
            toChildren = { children.asSequence() },
            equals = { this.isEqualNode(it) },
            replaceWith = { this.replaceWith(it) }
        )
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun <T> replaceIfNew(
        items: List<Pair<T, T>>,
        toChildren: T.() -> Sequence<T>,
        equals: T.(T) -> Boolean,
        replaceWith: T.(T) -> Unit
    ) {
        items
            .map { (old, new) ->
                val areIdentical = old.isDeepEqual(new, toChildren, equals)
                Triple(areIdentical, old, new) }
            .forEach { (areIdentical, old, new) ->
                if (areIdentical) {
                    log("New element identical to previous. Not updating node: ${JSON.stringify(new)}")
                } else {
                    old.replaceWith(new)
                }
            }
    }

    private fun <T> ItemArrayLike<T>.asSequence(): Sequence<T> {
        val length = this.length
        return iterator<T> {
            for (i in 0 until length) {
                item(i)?.let { yield(it) }
            }
        }.asSequence()
    }

    private fun <T> T.isDeepEqual(
        that: T,
        toChildren: T.() -> Sequence<T>,
        equals: T.(T) -> Boolean
    ): Boolean {
        val thisDesc = this.descendants(toChildren)
        val thatDesc = that.descendants(toChildren)

        return thisDesc.allEqual(thatDesc, equals)
    }

    private fun <T> T.descendants(toChildren: T.() -> Sequence<T>): Sequence<T> =
        sequenceOf(this)
            .plus(toChildren().asSequence().flatMap { child -> child.descendants(toChildren) })

    private fun <T> Sequence<T>.allEqual(that: Sequence<T>, equal: (T, T) -> Boolean): Boolean {
        /*
        To be efficient, this should be:
        - Compare heads
        - Recurse with tails

        But there's no head:tail method, so that runs into "sequence can't be used twice."

        Is there another approach?
         */
        val l1 = this.toList()
        val l2 = that.toList()

        if (l1.size != l2.size) return false
        return l1.zip(l2).all { equal(it.first, it.second) }
    }
}

fun log(text: String) {
    println("ViewUpdater: $text")
}
