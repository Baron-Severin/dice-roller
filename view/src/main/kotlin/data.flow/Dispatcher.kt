package data.flow

import domain.objects.Pager

class Dispatcher(private val store: Store) {
    fun dispatchInit() {
        store.apply(Event.Init)
    }

    fun dispatchPagerClicked(pager: Pager) {
        store.apply(Event.PagerTabClicked(pager))
    }

    fun dispatchRollClicked(firstInt: Int?, secondInt: Int?) {
        store.apply(Event.RollClicked(firstInt, secondInt))
    }
}
