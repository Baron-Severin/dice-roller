import components.PagerTabsDisplay
import data.flow.Dispatcher
import data.flow.State
import components.CheckResultDisplay
import components.InputDisplay
import kotlin.browser.window

private lateinit var dispatcher: Dispatcher

fun main() {
    window.onload = {
        val serviceLocator = ServiceLocator(::display)
        dispatcher = serviceLocator.dispatcher
        dispatcher.dispatchInit()
    }
}

private fun display(state: State) {
    ViewUpdater.replaceIfNew(
        listOf(
            pagerTabContainer to PagerTabsDisplay(state.selectedPager, dispatcher),
            inputContainer to InputDisplay(state.selectedPager, dispatcher),
            checkResultContainer to CheckResultDisplay(state.currentRollResults)
        )
    )

    HtmlHacks.initInputs()
}
