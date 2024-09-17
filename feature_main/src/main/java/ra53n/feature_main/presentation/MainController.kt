package ra53n.feature_main.presentation

import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.runtime.Stable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import ra53n.feature_photo.PhotoManager
import ra53n.scan_thing.core.flux.Dispatcher
import ra53n.scan_thing.core.flux.Fail
import ra53n.scan_thing.core.flux.IAction
import ra53n.scan_thing.core.flux.IController
import ra53n.scan_thing.core.flux.Success
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

@Stable
class MainController @Inject constructor(
    private val photoManager: PhotoManager,
) : IController<MainState> {
    override val store = MainStore()
    override val dispatcher = Dispatcher().apply {
        add(store = store)
    }
    private val scope = CoroutineScope(Dispatchers.IO)
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    override fun dispatch(action: IAction) {
        when (action) {
            is MainAction.TakePhoto -> {
                photoManager.takePhoto(
                    imageCapture = action.imageCapture,
                    outputDirectory = action.outputDirectory,
                    executor = executor,
                    onImageCaptured = ::onImageCaptured,
                    onError = ::onImageCaptureException
                )
            }

            is MainAction.Back -> {
                action.uri.path?.let { File(it).delete() }
                dispatcher.handle(MainChange.Back)
            }
        }
    }

    private fun onImageCaptured(uri: Uri) {
        dispatcher.handle(MainChange.UpdatePhotoState(Success(uri)))
    }

    private fun onImageCaptureException(exception: ImageCaptureException) {
        dispatcher.handle(MainChange.UpdatePhotoState(Fail(exception)))
    }

    override fun clear() {
        scope.cancel()
        executor.shutdown()
    }
}

sealed class MainAction : IAction {
    data class TakePhoto(val outputDirectory: File, val imageCapture: ImageCapture) : MainAction()
    data class Back(val uri: Uri) : MainAction()
}
