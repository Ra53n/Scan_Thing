package ra53n.scan_thing.feature_scan_photo.data

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import ra53n.scan_thing.feature_scan_photo.domain.DetectMode
import ra53n.scan_thing.feature_scan_photo.domain.DetectedObject
import ra53n.scan_thing.feature_scan_photo.domain.ScanPhotoRepository
import java.io.File
import javax.inject.Inject

open class ScanPhotoRepositoryImpl @Inject constructor(
    private val service: ScanPhotoService,
    private val mapper: DetectObjectMapper
) : ScanPhotoRepository {

    override suspend fun scanPhoto(photo: File, mode: DetectMode): Flow<DetectedObject> {
        val requestFile: RequestBody = photo.asRequestBody(MultipartBody.FORM)
        val body = MultipartBody.Part.createFormData("file", photo.name, requestFile)

        val metadata = ScanRequestMetadata(
            mode = listOf(mode.mode),
            images = listOf(ScanRequestImage("file"))
        )

        val metaRequestBody = Gson().toJson(metadata)
            .toRequestBody("application/json".toMediaTypeOrNull())

        return flow {
            emit(mapper.map(service.detectObjects(image = body, meta = metaRequestBody)))
        }
    }
}

data class ScanRequestMetadata(
    val mode: List<String>,
    val images: List<ScanRequestImage>
)

data class ScanRequestImage(
    val name: String
)
