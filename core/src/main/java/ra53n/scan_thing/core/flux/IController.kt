package ra53n.scan_thing.core.flux

interface IController<STATE : IState> {
    val store: AbstractStore<STATE>
    val dispatcher: Dispatcher

    val currentValue: STATE
        get() = store.state().value

    fun state() = store.state()

    fun dispatch(action: IAction)

    fun clear() {

    }
}