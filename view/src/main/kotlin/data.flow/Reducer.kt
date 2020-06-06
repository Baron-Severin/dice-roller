package data.flow

fun reduce(current: State, event: Event): State = when (event) {
    is Event.Init -> current
    is Event.PagerTabClicked -> reduce(current, event)
    is Event.RollClicked -> reduce(current, event)
}

private fun reduce(state: State, event: Event.PagerTabClicked) = state.copy(
    selectedPager = event.pager
)

private fun reduce(state: State, event: Event.RollClicked) = state.copy(
    currentRollResults = Check.dramatic(event.firstInput!!) // TODO
)
