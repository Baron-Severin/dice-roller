
object Constants {
    object Id {
        // Containers
        const val PAGER_TAB_CONTAINER = "PAGER_TAB_CONTAINER"
        const val INPUT_CONTAINER = "INPUT_CONTAINER"
        const val CHECK_RESULT_CONTAINER = "CHECK_RESULT_CONTAINER"
        const val LOG_CONTAINER = "LOG_CONTAINER"

        // Inputs
        const val ACTOR_INPUT_ID = "ACTOR_INPUT_ID"
        const val RECEIVER_INPUT_ID = "RECEIVER_INPUT_ID"

        // Buttons
        const val ROLL_BUTTON = "ROLL_BUTTON"

        // Wrappers
        const val LOG_RESULT_WRAPPER = "LOG_RESULT_WRAPPER"
    }
    object Css {
        object Class {
            private object MaterialIo {
                const val CARD = "mdc-card"
                const val FLAT_CARD = "mdc-card mdc-card--outlined"
            }

            const val NONE = ""
            const val COLOR_INDETERMINATE_SUCCESS = "color-indeterminate-success"
            const val COLOR_GOOD = "color-good"
            const val FILTER_GOOD = "filter-good"
            const val COLOR_BAD = "color-bad"
            const val FILTER_BAD = "filter-bad"
            const val CARD = "card ${MaterialIo.CARD}"
            const val FLAT_CARD = "card flat-card ${MaterialIo.FLAT_CARD}"
            const val PAGER_TAB = "pager-tab"
            const val PAGER_TAB_ACTIVE = "pager-tab-active"
            const val THICK_BORDER = "thick-border"
            const val HEADER = "header"
        }
    }
}
