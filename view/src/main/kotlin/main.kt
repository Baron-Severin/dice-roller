import components.PagerTabsDisplay
import data.flow.Dispatcher
import data.flow.State
import domain.objects.CheckResultDisplay
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement
import kotlin.browser.document
import kotlin.browser.window

val checkResultContainer get() = document.getElementById(Constants.Id.CHECK_RESULT_CONTAINER) as HTMLElement
val pagerTabContainer get() = document.getElementById(Constants.Id.PAGER_TAB_CONTAINER) as HTMLElement

private lateinit var dispatcher: Dispatcher

fun main() {
    window.onload = {
        val serviceLocator = ServiceLocator(::display)
        dispatcher = serviceLocator.dispatcher
        dispatcher.dispatchInit()


        val thresholdInput = document.getElementById("threshold") as HTMLInputElement
        val rollButton = document.getElementById("roll") as HTMLButtonElement

        rollButton.addEventListener("click", { // TODO move to dispatcher
            val threshold = thresholdInput.value.toIntOrNull()

            dispatcher.dispatchRollClicked(threshold, null)
        })
    }
}

fun display(state: State) {
    pagerTabContainer.replaceWith(PagerTabsDisplay(state.selectedPager, dispatcher))
    checkResultContainer.replaceWith(CheckResultDisplay(state.currentRollResults))
}
