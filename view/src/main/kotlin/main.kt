import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLParagraphElement
import kotlin.browser.document
import kotlin.browser.window

fun main() {
    window.setTimeout({ // todo replace with ondocumentload
        val thresholdInput = document.getElementById("threshold") as HTMLInputElement
        val rollButton = document.getElementById("roll") as HTMLButtonElement
        val didSucceed = document.getElementById("didSucceed") as HTMLParagraphElement
        val successLevelsP = document.getElementById("successLevels") as HTMLParagraphElement

        rollButton.addEventListener("click", {
            val threshold = thresholdInput.value.toInt() ?: 0

//            // TODO expose TestRoller in a nicer way
            val result = TestRoller(RealDiceRoll()).dramaticTest(threshold)
            didSucceed.innerHTML = "Did Succeed: ${result.didSucceed}"
            successLevelsP.innerHTML = "Success Levels: ${result.successLevels}"
            console.log(result)

        })
    }, 1_000)
}
