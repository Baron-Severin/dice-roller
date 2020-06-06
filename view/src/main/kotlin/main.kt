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
        val checkInputs = document.getElementById("checkInputs") as HTMLParagraphElement

        rollButton.addEventListener("click", {
            val threshold = thresholdInput.value.toInt()

            // TODO expose TestRoller in a nicer way
            val result = Check.dramaticCheck(threshold)
            didSucceed.innerHTML = "Did Succeed: ${wrapSuccessFail(result.didSucceed, result.didSucceed)}"
            checkInputs.innerHTML = "Roll/Skill Check: ${result.inputs.roll}/${result.inputs.threshold} (${wrapSuccessFail(result.inputs.margin, result.inputs.margin >= 0)})"
            successLevelsP.innerHTML = "Success Levels: ${wrapSuccessFail(result.successLevels, result.successLevels >= 0)}"
        })
    }
}

private fun wrapSuccessFail(text: Any, didSucceed: Boolean): String = wrapSuccessFail(text.toString(), didSucceed)

private fun wrapSuccessFail(text: String, didSucceed: Boolean): String {
    val color = if (didSucceed) "green" else "red"
    val span = "<span style='color: ${color}'>" to "</span>"
    return "${span.first}$text${span.second}"
}
