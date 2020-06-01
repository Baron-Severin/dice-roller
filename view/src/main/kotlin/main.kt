import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLParagraphElement
import kotlin.browser.document
import kotlin.browser.window

fun main() {
    // TODO this looks neat.  Use it. https://kotlinlang.org/docs/tutorials/javascript/typesafe-html-dsl.html
    window.onload = {
        val thresholdInput = document.getElementById("threshold") as HTMLInputElement
        val rollButton = document.getElementById("roll") as HTMLButtonElement
        val didSucceed = document.getElementById("didSucceed") as HTMLParagraphElement
        val successLevelsP = document.getElementById("successLevels") as HTMLParagraphElement

        rollButton.addEventListener("click", {
            val threshold = thresholdInput.value.toInt()

            // TODO expose TestRoller in a nicer way
            val result = Test.dramaticTest(threshold)
            didSucceed.innerHTML = "Did Succeed: ${result.didSucceed}"
            successLevelsP.innerHTML = "Success Levels: ${result.successLevels}"
        })
    }
}
