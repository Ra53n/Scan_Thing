package ra53n.scan_thing.core.flux

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

abstract class AbstractStore<STATE : IState>(initState: STATE) {
    protected val scope =
        CoroutineScope(Executors.newSingleThreadExecutor().asCoroutineDispatcher())
    protected val state = MutableStateFlow(initState)

    fun state(): StateFlow<STATE> = state

    fun handle(action: IChange) {
        scope.launch {
            val newState = newState(state.value, action)
            moveToState(newState)
        }
    }

    protected fun moveToState(newState: STATE) {
        if (newState == state.value) {
            return
        }
        state.value = newState
    }

    protected open fun newState(oldState: STATE, action: IChange): STATE {
        throw IllegalStateException("Can't handle action $action")
    }
}