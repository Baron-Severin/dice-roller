import components.CHECK_RESULT_ID
import components.CheckResultDisplay
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLInputElement
import kotlin.browser.document
import kotlin.browser.window

val checkResultContainer get() = document.getElementById(CHECK_RESULT_ID) as HTMLDivElement

fun main() {
    window.onload = {
        val thresholdInput = document.getElementById("threshold") as HTMLInputElement
        val rollButton = document.getElementById("roll") as HTMLButtonElement

        rollButton.addEventListener("click", {
            val threshold = thresholdInput.value.toInt()

            val result = Check.dramatic(threshold)

            checkResultContainer.replaceWith(CheckResultDisplay(result))
        })
    }
}
