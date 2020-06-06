import data.flow.Dispatcher
import data.flow.State
import data.flow.Store

class ServiceLocator(display: (State, State?) -> Unit) {
    private val store = Store(display = display)
    val dispatcher = Dispatcher(store)
}
