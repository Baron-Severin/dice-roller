package components

import kotlinx.html.*
import kotlinx.html.dom.create
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLSpanElement
import kotlin.browser.document

const val CHECK_RESULT_ID = "CHECK_RESULT_ID"

fun ResultDisplay(
    result: CheckResult.Dramatic
): HTMLElement = document.create.div {
    val spanClass = if (result.didSucceed) "color-success" else "color-failure"

    id = CHECK_RESULT_ID
    p {
        +"Did succeed: "
        span(classes = spanClass) { +result.didSucceed.toString() }
    }
    p {
        +"Roll/Skill Check: ${result.inputs.roll}/${result.inputs.threshold}"
        span(classes = spanClass) { +result.inputs.margin.toString() }
    }
    p {
        +"Success Levels: "
        span(classes = spanClass) { +result.successLevels.toString() }
    }
}
