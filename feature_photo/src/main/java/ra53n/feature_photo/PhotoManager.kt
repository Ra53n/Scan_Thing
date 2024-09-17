package ra53n.feature_photo

import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import java.io.File
import java.util.concurrent.ExecutorService
import javax.inject.Inject

class PhotoManager @Inject constructor(
    private val fileUtils: FileUtils
) {

    fun takePhoto(
        imageCapture: ImageCapture,
        executor: ExecutorService,
        outputDirectory: File,
        onImageCaptured: (Uri) -> Unit,
        onError: (ImageCaptureException) -> Unit
    ) {

        val photoFile = fileUtils.createPhotoFile(outputDirectory)

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            executor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exception: ImageCaptureException) {
                    onError(exception)
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    onImageCaptured(savedUri)
                }
            }
        )
    }
}