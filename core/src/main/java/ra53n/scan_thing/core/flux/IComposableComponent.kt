package ra53n.scan_thing.core.flux

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState

interface IComposableComponent<STATE : IState, CONTROLLER : IController<STATE>> {
    val controller: CONTROLLER

    @Composable
    operator fun invoke() {
        Content(state = controller.state().collectAsState())
    }

    @Composable
    fun Content(state: State<STATE>)
}