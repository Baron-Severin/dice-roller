import components.PagerTabsDisplay
import domain.objects.CheckResultDisplay
import domain.objects.Pager
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLInputElement
import kotlin.browser.document
import kotlin.browser.window

val checkResultContainer get() = document.getElementById(Constants.Id.CHECK_RESULT_CONTAINER) as HTMLDivElement
val pagerTabContainer get() = document.getElementById(Constants.Id.PAGER_TAB_CONTAINER) as HTMLDivElement

fun main() {
    window.onload = {
        pagerTabContainer.replaceWith(PagerTabsDisplay(Pager.SIMPLE_CHECK))

        val thresholdInput = document.getElementById("threshold") as HTMLInputElement
        val rollButton = document.getElementById("roll") as HTMLButtonElement

        rollButton.addEventListener("click", { // TODO move to dispatcher
            val threshold = thresholdInput.value.toIntOrNull() ?: return@addEventListener

            val result = Check.dramatic(threshold)

            checkResultContainer.replaceWith(CheckResultDisplay(result))
        })
    }
}
