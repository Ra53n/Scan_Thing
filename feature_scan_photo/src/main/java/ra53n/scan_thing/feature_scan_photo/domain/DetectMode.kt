package ra53n.scan_thing.feature_scan_photo.domain

enum class DetectMode(val mode: String) {
    OBJECT2("object2"),
    SCENE("scene"),
    CAR_NUMBER("car_number"),
    MULTIOBJECT("multiobject"),
}