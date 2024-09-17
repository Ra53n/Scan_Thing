package ra53n.scan_thing.feature_scan_photo.domain

import kotlinx.coroutines.flow.Flow
import java.io.File

interface ScanPhotoRepository {

    suspend fun scanPhoto(photo: File, mode: DetectMode): Flow<DetectedObject>
}