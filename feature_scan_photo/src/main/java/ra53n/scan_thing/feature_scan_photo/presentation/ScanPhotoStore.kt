package ra53n.scan_thing.feature_scan_photo.presentation

import android.net.Uri
import ra53n.scan_thing.core.flux.AbstractStore
import ra53n.scan_thing.core.flux.IChange
import ra53n.scan_thing.core.flux.IState
import ra53n.scan_thing.core.flux.Pending
import ra53n.scan_thing.core.flux.States
import ra53n.scan_thing.feature_scan_photo.domain.DetectedObject

class ScanPhotoStore : AbstractStore<ScanPhotoState>(ScanPhotoState()) {
    override fun newState(oldState: ScanPhotoState, action: IChange): ScanPhotoState {
        return when (action) {
            is ScanPhotoChange.UpdateScanPhotoState -> {
                return oldState.copy(
                    scanPhotoState = action.state
                )
            }

            is ScanPhotoChange.UpdatePhotoUri -> {
                return oldState.copy(
                    photoUri = action.uri
                )
            }

            is ScanPhotoChange.UpdateSelectedLabel -> {
                return oldState.copy(
                    selectedLabelIndex = action.index
                )
            }

            else -> super.newState(oldState, action)
        }
    }
}

data class ScanPhotoState(
    val photoUri: Uri = Uri.EMPTY,
    val scanPhotoState: States<DetectedObject?> = Pending(),
    val selectedLabelIndex: Int = -1
) : IState

sealed class ScanPhotoChange : IChange {
    data class UpdateScanPhotoState(val state: States<DetectedObject?>) : ScanPhotoChange()

    data class UpdatePhotoUri(val uri: Uri) : ScanPhotoChange()

    data class UpdateSelectedLabel(val index: Int) : ScanPhotoChange()

}