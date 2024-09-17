package ra53n.scan_thing.feature_scan_photo.presentation

import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import ra53n.scan_thing.core.flux.Dispatcher
import ra53n.scan_thing.core.flux.Fail
import ra53n.scan_thing.core.flux.IAction
import ra53n.scan_thing.core.flux.IController
import ra53n.scan_thing.core.flux.Pending
import ra53n.scan_thing.core.flux.Success
import ra53n.scan_thing.feature_scan_photo.domain.ScanPhotoInteractor
import java.io.File
import javax.inject.Inject

class ScanPhotoController @Inject constructor(
    private val scanPhotoInteractor: ScanPhotoInteractor
) : IController<ScanPhotoState> {
    override val store = ScanPhotoStore()
    override val dispatcher = Dispatcher().apply {
        add(store = store)
    }

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun dispatch(action: IAction) {
        when (action) {
            is ScanPhotoAction.ScanPhoto -> {
                scanPhoto(action.uri)
            }

            is ScanPhotoAction.UpdateSelectedLabel -> {
                dispatcher.handle(ScanPhotoChange.UpdateSelectedLabel(index = action.index))
            }
        }
    }

    private fun scanPhoto(uri: Uri?) {
        dispatcher.handle(ScanPhotoChange.UpdateScanPhotoState(state = Pending()))
        if (uri == null) {
            dispatcher.handle(ScanPhotoChange.UpdateScanPhotoState(state = Fail(Exception("Can't scan photo without uri"))))
            return
        }
        dispatcher.handle(ScanPhotoChange.UpdatePhotoUri(uri = uri))
        scope.launch {
            uri.path?.let {
                scanPhotoInteractor.scanPhoto(File(it))
                    .catch { exception ->
                        dispatcher.handle(
                            ScanPhotoChange.UpdateScanPhotoState(state = Fail(exception))
                        )
                    }
                    .collect { result ->
                        dispatcher.handle(
                            ScanPhotoChange.UpdateScanPhotoState(
                                state = Success(result = result)
                            )
                        )
                    }
            }
        }
    }

    override fun clear() {
        scope.cancel()
    }
}

sealed class ScanPhotoAction : IAction {
    data class ScanPhoto(val uri: Uri?) : ScanPhotoAction()
    data class UpdateSelectedLabel(val index: Int) : ScanPhotoAction()
}