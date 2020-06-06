package data.flow

import domain.objects.Pager

sealed class Event {
    object Init : Event()
    data class PagerTabClicked(val pager: Pager) : Event()
    data class RollClicked(val firstInput: Int?, val secondInput: Int?) : Event()
}
