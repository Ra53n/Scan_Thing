package ra53n.scan_thing.feature_scan_photo.data

import ra53n.scan_thing.feature_scan_photo.domain.Label
import javax.inject.Inject

open class LabelMapper @Inject constructor() {

    open fun map(response: LabelResponse): Label {
        return Label(
            name = response.rus,
            x1 = response.coord.getOrNull(0) ?: 0f,
            y1 = response.coord.getOrNull(1) ?: 0f,
            x2 = response.coord.getOrNull(2) ?: 0f,
            y2 = response.coord.getOrNull(3) ?: 0f
        )
    }
}