package ra53n.scan_thing.feature_scan_photo.domain

import kotlinx.coroutines.flow.Flow
import ra53n.scan_thing.feature_scan_photo.data.ScanPhotoRepositoryImpl
import java.io.File
import javax.inject.Inject


class ScanPhotoInteractorImpl @Inject constructor(
    private val repository: ScanPhotoRepositoryImpl
) : ScanPhotoInteractor {

    override suspend fun scanPhoto(photo: File): Flow<DetectedObject> {
        return repository.scanPhoto(photo, DetectMode.OBJECT2)
    }
}