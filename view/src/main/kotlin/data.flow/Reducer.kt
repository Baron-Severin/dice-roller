package data.flow

import domain.objects.Pager

fun reduce(current: State, event: Event): State = when (event) {
    is Event.Init -> current
    is Event.PagerTabClicked -> reduce(current, event)
    is Event.RollClicked -> reduce(current, event)
}

private fun reduce(state: State, event: Event.PagerTabClicked): State {
    val rollResults = if (state.selectedPager != event.pager) {
        CheckResult.None
    } else {
        state.currentRollResults
    }

    return state.copy(
        selectedPager = event.pager,
        currentRollResults = rollResults
    )
}

private fun reduce(state: State, event: Event.RollClicked): State {
    val (actorInput, receiverInput) = if (event.firstInput == null && event.secondInput != null) {
        event.secondInput to event.firstInput
    } else {
        event.firstInput to event.secondInput
    }

    if (actorInput == null) return state

    val checkResult = when (state.selectedPager) {
        Pager.SIMPLE_CHECK -> Check.simple(actorInput)
        Pager.DRAMATIC_CHECK -> Check.dramatic(actorInput)
        Pager.OPPOSED_CHECK -> Check.opposed(actorInput, receiverInput)
        Pager.COMBAT_CHECK -> Check.combat(actorInput, receiverInput)
    }

    return state.copy(
        currentRollResults = checkResult,
        logs = (listOf(state.currentRollResults) + state.logs)
            .filter { it !is CheckResult.None }
            .take(4)
    )
}
