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

val checkResultContainer get() = document.getElementById(Constants.Id.CHECK_RESULT_CONTAINER) as HTMLElement
val inputContainer get() = document.getElementById(Constants.Id.INPUT_CONTAINER) as HTMLElement
val pagerTabContainer get() = document.getElementById(Constants.Id.PAGER_TAB_CONTAINER) as HTMLElement

private lateinit var dispatcher: Dispatcher

fun main() {
    window.onload = {
        val serviceLocator = ServiceLocator(::display)
        dispatcher = serviceLocator.dispatcher
        dispatcher.dispatchInit()


//        val thresholdInput = document.getElementById("threshold") as HTMLInputElement
//        val rollButton = document.getElementById("roll") as HTMLButtonElement
//
//        rollButton.addEventListener("click", { // TODO move to dispatcher
//            val threshold = thresholdInput.value.toIntOrNull()
//
//            dispatcher.dispatchRollClicked(threshold, null)
//        })
    }
}

fun display(state: State, previousState: State?) {
    if (state.selectedPager != previousState?.selectedPager) {
        pagerTabContainer.replaceWith(PagerTabsDisplay(state.selectedPager, dispatcher))
        inputContainer.replaceWith(InputDisplay(state.selectedPager, dispatcher))
    }
    if (state.currentRollResults != previousState?.currentRollResults) {
        checkResultContainer.replaceWith(CheckResultDisplay(state.currentRollResults))
    }
}
