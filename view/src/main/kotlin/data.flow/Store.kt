package data.flow

import CheckResult
import domain.objects.Pager

private val initialState = State(
    selectedPager = Pager.SIMPLE_CHECK,
    currentRollResults = CheckResult.None,
    logs = emptyList()
)

class Store(
    private var currentState: State = initialState,
    private val display: (State) -> Unit
) {
    fun apply(event: Event) {
        log("Applying event: $event")
        currentState = reduce(currentState, event)
        log("New state: $currentState")
        display(currentState)
    }
}

data class State(
    val selectedPager: Pager,
    // TODO user input stuff
    val currentRollResults: CheckResult,
    val logs: List<CheckResult>
)

fun log(text: String) {
    println("Store: $text")
}
