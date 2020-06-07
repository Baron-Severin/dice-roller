import components.PagerTabsDisplay
import data.flow.Dispatcher
import data.flow.State
import components.CheckResultDisplay
import components.InputDisplay
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement
import kotlin.browser.document
import kotlin.browser.window

private fun get(id: String): HTMLElement = document.getElementById(id) as HTMLElement

val checkResultContainer get() = get(Constants.Id.CHECK_RESULT_CONTAINER)
val inputContainer get() = get(Constants.Id.INPUT_CONTAINER)
val pagerTabContainer get() = get(Constants.Id.PAGER_TAB_CONTAINER)
val actorInput get() = get(Constants.Id.ACTOR_INPUT_ID)

private lateinit var dispatcher: Dispatcher

fun main() {
    window.onload = {
        val serviceLocator = ServiceLocator(::display)
        dispatcher = serviceLocator.dispatcher
        dispatcher.dispatchInit()
    }
}

fun display(state: State, previousState: State?) {
    if (state.selectedPager != previousState?.selectedPager) {
        pagerTabContainer.replaceWith(PagerTabsDisplay(state.selectedPager, dispatcher))
        inputContainer.replaceWith(InputDisplay(state.selectedPager, dispatcher))
        actorInput.focus()
    }
    if (state.currentRollResults != previousState?.currentRollResults) {
        checkResultContainer.replaceWith(CheckResultDisplay(state.currentRollResults))
    }
}
