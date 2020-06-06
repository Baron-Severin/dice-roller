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

            val result = Check.dramatic(threshold)
            didSucceed.innerHTML = "Did Succeed: ${colorize(result.didSucceed, result)}"
            checkInputs.innerHTML = "Roll/Skill Check: ${result.inputs.roll}/${result.inputs.threshold} (${colorize(result.inputs.margin, result)})"
            successLevelsP.innerHTML = "Success Levels: ${colorize(result.successLevels, result)}"
        })
    }
}

private fun colorize(text: Any, result: DramaticCheckResult): String = colorize(text.toString(), result)

private fun colorize(text: String, result: DramaticCheckResult): String {
    val color = if (result.didSucceed) "green" else "red"
    val span = "<span style='color: ${color}'>" to "</span>"
    return "${span.first}$text${span.second}"
}
