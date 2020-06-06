@file:Suppress("FunctionName")

package components

import data.flow.Dispatcher
import domain.objects.Pager
import kotlinx.html.div
import kotlinx.html.dom.create
import kotlinx.html.id
import kotlinx.html.js.nav
import kotlinx.html.js.onClickFunction
import kotlinx.html.p
import org.w3c.dom.HTMLElement
import kotlin.browser.document

fun PagerTabsDisplay(selected: Pager, dispatcher: Dispatcher): HTMLElement {
    return document.create.nav {
        id = Constants.Id.PAGER_TAB_CONTAINER
        Pager.values().map { pager ->
            val cssClasses = listOfNotNull(
                Constants.Css.Class.CARD,
                Constants.Css.Class.PAGER_TAB,
                if (pager == selected) Constants.Css.Class.PAGER_TAB_ACTIVE else null
            ).joinToString(separator = " ")
            .trim()
            div(classes = cssClasses) {
                p { +pager.displayName }
                onClickFunction = {
                    dispatcher.dispatchPagerClicked(pager)
                }
            }
        }
    }
}
