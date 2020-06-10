import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement
import kotlin.browser.document

// Containers
val checkResultContainer get() = get(Constants.Id.CHECK_RESULT_CONTAINER)!!
val inputContainer get() = get(Constants.Id.INPUT_CONTAINER)!!
val pagerTabContainer get() = get(Constants.Id.PAGER_TAB_CONTAINER)!!
val logContainer get() = get(Constants.Id.LOG_CONTAINER)!!

// Inputs
val actorInput get() = get(Constants.Id.ACTOR_INPUT_ID) as HTMLInputElement
val receiverInput get() = get(Constants.Id.RECEIVER_INPUT_ID) as HTMLInputElement?

// Buttons
val rollButton get() = get(Constants.Id.ROLL_BUTTON) as HTMLButtonElement

private fun get(id: String) = document.getElementById(id) as HTMLElement?
