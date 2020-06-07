import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.KeyboardEvent

/**
 * Sets up things not currently supported by kotlinx.html
 */
object HtmlHacks {
    fun initInputs() {
        // Autofocus not supported
        actorInput.focus()
        setIncrementListener(actorInput)
        setIncrementListener(receiverInput)
    }

    // input step not supported
    private fun setIncrementListener(input: HTMLInputElement?) {
        if (input == null) return
        input.onkeydown = { e ->
            val modifier = when {
                e.isKeyUp() -> 10
                e.isKeyDown() -> -10
                else -> null
            }
            val value = input.value.toIntOrNull()

            if (modifier != null && value != null) {
                input.value = (value + modifier).toString()
                e.preventDefault()
            }
        }
    }
}

private fun KeyboardEvent.isKeyUp() = this.code == "ArrowUp"
private fun KeyboardEvent.isKeyDown() = this.code == "ArrowDown"
