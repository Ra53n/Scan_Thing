package ra53n.scan_thing.feature_scan_photo

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import ra53n.scan_thing.feature_scan_photo.data.DetectObjectMapper
import ra53n.scan_thing.feature_scan_photo.data.DetectObjectsResponseBody
import ra53n.scan_thing.feature_scan_photo.data.DetectObjectsResultResponse
import ra53n.scan_thing.feature_scan_photo.data.DetectedObjectResponse
import ra53n.scan_thing.feature_scan_photo.data.LabelMapper
import ra53n.scan_thing.feature_scan_photo.data.LabelResponse
import ra53n.scan_thing.feature_scan_photo.domain.Label

class DetectObjectMapperTest {

    private lateinit var labelMapper: LabelMapper
    private lateinit var detectObjectMapper: DetectObjectMapper

    @Before
    fun setup() {
        labelMapper = mock()
        detectObjectMapper = DetectObjectMapper(labelMapper)
    }

    @Test
    fun `map should correctly map DetectObjectsResultResponse to DetectedObject`() {
        val labelResponse = LabelResponse(
            rus = "Test Label",
            eng = "",
            coord = listOf(0.1f, 0.2f, 0.3f, 0.4f)
        )
        val detectedObjectResponse = DetectedObjectResponse(
            status = 200,
            name = "Object Name",
            labels = listOf(labelResponse)
        )
        val response = DetectObjectsResultResponse(
            status = 200,
            body = DetectObjectsResponseBody(
                objectLabels = listOf(detectedObjectResponse)
            )
        )
        val expectedLabel = Label(
            name = "Test Label",
            x1 = 0.1f,
            y1 = 0.2f,
            x2 = 0.3f,
            y2 = 0.4f
        )
        whenever(labelMapper.map(labelResponse)).thenReturn(expectedLabel)

        val detectedObject = detectObjectMapper.map(response)

        assertEquals("Object Name", detectedObject.name)
        assertEquals(listOf(expectedLabel), detectedObject.labels)
    }

    @Test
    fun `map should handle empty objectLabels list`() {
        val response = DetectObjectsResultResponse(
            status = 200,
            body = DetectObjectsResponseBody(
                objectLabels = emptyList()
            )
        )

        val detectedObject = detectObjectMapper.map(response)

        assertEquals("", detectedObject.name)
        assertEquals(emptyList<Label>(), detectedObject.labels)
    }

//    @Test
//    fun `map should handle null detectedObject`() {
//        val response = DetectObjectsResultResponse(
//            status = 200,
//            body = DetectObjectsResponseBody(
//                objectLabels = listOf(null)
//            )
//        )
//
//        val detectedObject = detectObjectMapper.map(response)
//
//        assertEquals("", detectedObject.name)
//        assertEquals(0, detectedObject.labels.size)
//    }
}