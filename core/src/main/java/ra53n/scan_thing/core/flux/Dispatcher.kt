package ra53n.scan_thing.core.flux

class Dispatcher {
    val stores = mutableSetOf<AbstractStore<*>>()

    fun handle(action: IChange) {
        stores.forEach {
            it.handle(action)
        }
    }

    fun add(store: AbstractStore<*>) {
        stores.add(store)
    }

    fun remove(store: AbstractStore<*>) {
        stores.remove(store)
    }
}