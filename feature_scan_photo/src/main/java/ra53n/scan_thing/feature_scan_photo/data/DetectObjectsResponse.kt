package ra53n.scan_thing.feature_scan_photo.data

import com.google.gson.annotations.SerializedName

data class DetectObjectsResultResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("body")
    val body: DetectObjectsResponseBody
)

data class DetectObjectsResponseBody(
    @SerializedName("object_labels")
    val objectLabels: List<DetectedObjectResponse> = emptyList(),
)

data class DetectedObjectResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("labels")
    val labels: List<LabelResponse>,
)

data class LabelResponse(
    @SerializedName("eng")
    val eng: String,
    @SerializedName("rus")
    val rus: String,
    @SerializedName("coord")
    val coord: List<Float>
)