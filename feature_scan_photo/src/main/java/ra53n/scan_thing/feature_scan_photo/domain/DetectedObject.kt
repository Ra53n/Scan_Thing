package ra53n.scan_thing.feature_scan_photo.domain

data class DetectedObject(
    val name: String,
    val labels: List<Label>
)

data class Label(
    val name: String,
    val x1: Float,
    val y1: Float,
    val x2: Float,
    val y2: Float
)