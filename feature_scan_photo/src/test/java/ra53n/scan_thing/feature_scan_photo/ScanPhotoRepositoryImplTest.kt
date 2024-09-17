package ra53n.scan_thing.feature_scan_photo

import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.whenever
import ra53n.scan_thing.feature_scan_photo.data.DetectObjectMapper
import ra53n.scan_thing.feature_scan_photo.data.DetectObjectsResponseBody
import ra53n.scan_thing.feature_scan_photo.data.DetectObjectsResultResponse
import ra53n.scan_thing.feature_scan_photo.data.ScanPhotoRepositoryImpl
import ra53n.scan_thing.feature_scan_photo.data.ScanPhotoService
import ra53n.scan_thing.feature_scan_photo.data.ScanRequestImage
import ra53n.scan_thing.feature_scan_photo.data.ScanRequestMetadata
import ra53n.scan_thing.feature_scan_photo.domain.DetectMode
import java.io.File

class ScanPhotoRepositoryImplTest {

    private lateinit var service: ScanPhotoService
    private lateinit var repository: ScanPhotoRepositoryImpl
    private lateinit var mapper: DetectObjectMapper
    private lateinit var gson: Gson
    private val successResult = DetectObjectsResultResponse(
        status = 200,
        body = DetectObjectsResponseBody(objectLabels = emptyList())
    )


    @Before
    fun setup() {
        service = mock()
        repository = ScanPhotoRepositoryImpl(service = service, mapper = mapper)
        gson = Gson()
    }

    @Test
    fun `scanPhoto should call service with correct parameters`() {
        runBlocking {
            val file = File("test.jpg")
            val mode = DetectMode.OBJECT2

            whenever(
                service.detectObjects(
                    oauthProvider = any(),
                    token = any(),
                    image = any(),
                    meta = any()
                )
            ).thenReturn(successResult)

            val result = repository.scanPhoto(file, mode).first()

            Assert.assertEquals(successResult, result)
        }
    }

    @Test
    fun `scanPhoto should create correct metadata`() {
        runBlocking {
            val file = File("test.jpg")
            val mode = DetectMode.OBJECT2
            val expectedMetadata = ScanRequestMetadata(
                mode = listOf(mode.mode),
                images = listOf(ScanRequestImage("file"))
            )
            val expectedMetaRequestBody = gson.toJson(expectedMetadata)
                .toRequestBody("application/json".toMediaTypeOrNull())

            whenever(
                service.detectObjects(
                    oauthProvider = any(),
                    token = any(),
                    image = any(),
                    meta = any()
                )
            ).thenReturn(successResult)

            repository.scanPhoto(file, mode).first()

            whenever(
                service.detectObjects(
                    oauthProvider = any(),
                    token = any(),
                    image = any(),
                    meta = eq(expectedMetaRequestBody)
                )
            ).thenReturn(successResult)
        }
    }
}