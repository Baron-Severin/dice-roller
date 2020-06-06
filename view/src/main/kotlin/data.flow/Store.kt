package data.flow

import CheckResult
import domain.objects.Pager

private val initialState = State(
    selectedPager = Pager.SIMPLE_CHECK,
    currentRollResults = CheckResult.None
)

class Store(
    private var currentState: State = initialState,
    private val display: (State, State?) -> Unit
) {
    fun apply(event: Event) {
        if (event is Event.Init) {
            display(currentState, null)
            return
        }

        val oldState = currentState
        log("Applying event: $event")
        currentState = reduce(currentState, event)
        log("New state: $currentState")
        display(currentState, oldState)
    }
}

data class State(
    val selectedPager: Pager,
    // TODO user input stuff
    val currentRollResults: CheckResult
    // TODO roll log
)

fun log(text: String) {
    println("Store: $text")
}