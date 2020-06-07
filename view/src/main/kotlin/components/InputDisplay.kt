@file:Suppress("FunctionName")

package components

import data.flow.Dispatcher
import domain.objects.Pager
import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.*
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement
import kotlin.browser.document

fun InputDisplay(selectedPager: Pager, dispatcher: Dispatcher) = when (selectedPager) {
    Pager.SIMPLE_CHECK, Pager.DRAMATIC_CHECK -> singleInput(dispatcher)
    Pager.OPPOSED_CHECK -> opposedInput(dispatcher)
    Pager.COMBAT_CHECK -> combatInput(dispatcher)
}

private fun sharedElements(
    firstInt: () -> Int?,
    secondInt: () -> Int?,
    dispatcher: Dispatcher,
    addInputs: DIV.() -> Unit
): HTMLElement = document.create.div {
    id = Constants.Id.INPUT_CONTAINER

    addInputs()

    button(type = ButtonType.submit) {
        id = Constants.Id.ROLL_BUTTON
        +"Roll"
        onClickFunction = { event ->
            dispatcher.dispatchRollClicked(firstInt(), secondInt())
            // Do not refresh
            event.preventDefault()
        }
    }
}

private fun singleInput(dispatcher: Dispatcher): HTMLElement {
    val input: DIV.() -> Unit = {
        input(type = InputType.number) {
            id = Constants.Id.ACTOR_INPUT_ID
            placeholder = "Difficulty"
        }
    }

    return sharedElements(
        firstInt = { getFirstInputValue() },
        secondInt = { null },
        dispatcher = dispatcher,
        addInputs = input
    )
}

private fun opposedInput(dispatcher: Dispatcher): HTMLElement {
    val opposedBlock: DIV.() -> Unit = {
        input(type = InputType.number) {
            id = Constants.Id.ACTOR_INPUT_ID
            placeholder = "Actor Difficulty"
        }
        input(type = InputType.number) {
            id = Constants.Id.RECEIVER_INPUT_ID
            placeholder = "Receiver Difficulty"
        }
    }

    return sharedElements(
        firstInt = { getFirstInputValue() },
        secondInt = { getSecondInputValue() },
        dispatcher = dispatcher,
        addInputs = opposedBlock
    )
}

private fun combatInput(dispatcher: Dispatcher): HTMLElement {
    val combatBlock: DIV.() -> Unit = {
        input(type = InputType.number) {
            id = Constants.Id.ACTOR_INPUT_ID
            placeholder = "Attacker Difficulty"
        }
        input(type = InputType.number) {
            id = Constants.Id.RECEIVER_INPUT_ID
            placeholder = "Defender Difficulty"
        }
    }

    return sharedElements(
        firstInt = { getFirstInputValue() },
        secondInt = { getSecondInputValue() },
        dispatcher = dispatcher,
        addInputs = combatBlock
    )
}

// Workaround for unimplemented onFocus and onChange listeners
private fun getFirstInputValue(): Int? = (document.getElementById(Constants.Id.ACTOR_INPUT_ID) as HTMLInputElement?)
    ?.value?.toIntOrNull()

// Workaround for unimplemented onFocus and onChange listeners
private fun getSecondInputValue(): Int? = (document.getElementById(Constants.Id.RECEIVER_INPUT_ID) as HTMLInputElement?)
    ?.value?.toIntOrNull()
