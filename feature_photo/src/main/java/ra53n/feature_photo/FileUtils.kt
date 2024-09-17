package ra53n.feature_photo

import java.io.File
import javax.inject.Inject

open class FileUtils @Inject constructor(
    private val dateFormatterUtils: DateFormatterUtils
) {

    fun createPhotoFile(
        outputDirectory: File,
        filenameFormat: String = DEFAULT_FILE_NAME_FORMAT,
    ): File {
        return File(
            outputDirectory,
            dateFormatterUtils.format(
                date = System.currentTimeMillis(),
                pattern = filenameFormat
            ) + DEFAULT_FILE_EXTENSION
        )
    }

    companion object {
        const val DEFAULT_FILE_NAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        const val DEFAULT_FILE_EXTENSION = ".jpg"
    }
}