@file:Suppress("FunctionName")

package components

import CheckResult
import data.flow.Dispatcher
import domain.objects.Pager
import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.*
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement
import kotlin.browser.document

fun LogDisplay(logs: List<CheckResult>) = document.create.div {
    id = Constants.Id.LOG_CONTAINER


    if (logs.any { it !is CheckResult.None }) {
        classes = setOf(Constants.Css.Class.THICK_BORDER)
        p(classes = Constants.Css.Class.HEADER) {
            +"Roll Log"
        }
    }
}.apply {
    logs.map {
        CheckResultDisplay(it, isPrimaryDisplay = false)
    }.forEach { append(it) }
}
