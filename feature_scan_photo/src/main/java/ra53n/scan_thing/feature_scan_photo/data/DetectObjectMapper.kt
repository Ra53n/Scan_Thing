package ra53n.scan_thing.feature_scan_photo.data

import ra53n.scan_thing.feature_scan_photo.domain.DetectedObject
import javax.inject.Inject

class DetectObjectMapper @Inject constructor(
    private val labelMapper: LabelMapper
) {

    fun map(response: DetectObjectsResultResponse): DetectedObject {
        val detectedObject = response.body.objectLabels.firstOrNull()
        return DetectedObject(
            name = detectedObject?.name.orEmpty(),
            labels = detectedObject?.labels.orEmpty().map { labelMapper.map(it) }
        )
    }
}