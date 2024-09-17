package ra53n.feature_main.presentation

import android.net.Uri
import ra53n.scan_thing.core.flux.AbstractStore
import ra53n.scan_thing.core.flux.IChange
import ra53n.scan_thing.core.flux.IState
import ra53n.scan_thing.core.flux.Pending
import ra53n.scan_thing.core.flux.States

class MainStore : AbstractStore<MainState>(MainState()) {

    override fun newState(oldState: MainState, action: IChange): MainState {
        return when (action) {
            is MainChange.UpdatePhotoState -> {
                oldState.copy(photoState = action.state)
            }

            is MainChange.Back -> {
                oldState.copy(photoState = Pending())
            }

            else -> super.newState(oldState, action)
        }
    }
}

data class MainState(
    val photoState: States<Uri> = Pending(),
) : IState

sealed class MainChange : IChange {
    data class UpdatePhotoState(val state: States<Uri>) : MainChange()
    data object Back : MainChange()
}