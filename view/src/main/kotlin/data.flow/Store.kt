package data.flow

import CheckResult
import domain.objects.Pager

private val initialState = State(
    selectedPager = Pager.SIMPLE_CHECK,
    currentRollResults = CheckResult.None
)

class Store(
    private var currentState: State = initialState,
    private val display: (State) -> Unit
) {
    fun apply(event: Event) {
        currentState = reduce(currentState, event)
        println("Applied event: $event")
        println("New state: $currentState")
        display(currentState)
    }
}

data class State(
    val selectedPager: Pager,
    // TODO user input stuff
    val currentRollResults: CheckResult
    // TODO roll log
)
