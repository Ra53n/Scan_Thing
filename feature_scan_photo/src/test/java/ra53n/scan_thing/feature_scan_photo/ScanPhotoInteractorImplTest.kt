package ra53n.scan_thing.feature_scan_photo

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import ra53n.scan_thing.feature_scan_photo.data.ScanPhotoRepositoryImpl
import ra53n.scan_thing.feature_scan_photo.domain.DetectMode
import ra53n.scan_thing.feature_scan_photo.domain.DetectedObject
import ra53n.scan_thing.feature_scan_photo.domain.ScanPhotoInteractorImpl
import java.io.File

class ScanPhotoInteractorImplTest {

    private lateinit var repository: ScanPhotoRepositoryImpl
    private lateinit var interactor: ScanPhotoInteractorImpl
    private val successResult = DetectedObject(
        name = "test",
        labels = emptyList()
    )


    @Before
    fun setup() {
        repository = mock()
        interactor = ScanPhotoInteractorImpl(repository)
    }

    @Test
    fun `scanPhoto should call repository with correct parameters`() {
        runBlocking {
            val file = File("test.jpg")

            whenever(repository.scanPhoto(any(), any())).thenReturn(flow { emit(successResult) })

            val result = interactor.scanPhoto(file).first()

            assertEquals(successResult, result)
        }
    }

    @Test
    fun `scanPhoto should use OBJECT2 mode`() {
        runBlocking {
            val file = File("test.jpg")

            whenever(repository.scanPhoto(any(), any())).thenReturn(flow { emit(successResult) })

            interactor.scanPhoto(file).first()

            whenever(repository.scanPhoto(any(), eq(DetectMode.OBJECT2))).thenReturn(flow {
                emit(
                    successResult
                )
            })
        }
    }
}