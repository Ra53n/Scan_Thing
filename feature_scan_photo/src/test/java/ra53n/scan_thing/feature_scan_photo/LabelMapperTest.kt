package ra53n.scan_thing.feature_scan_photo

import org.junit.Assert.assertEquals
import org.junit.Test
import ra53n.scan_thing.feature_scan_photo.data.LabelMapper
import ra53n.scan_thing.feature_scan_photo.data.LabelResponse
import ra53n.scan_thing.feature_scan_photo.domain.Label

class LabelMapperTest {

    private val labelMapper = LabelMapper()

    @Test
    fun `map should correctly map LabelResponse to Label`() {
        val response = LabelResponse(
            rus = "Test Label",
            eng = "",
            coord = listOf(0.1f, 0.2f, 0.3f, 0.4f)
        )
        val expectedLabel = Label(
            name = "Test Label",
            x1 = 0.1f,
            y1 = 0.2f,
            x2 = 0.3f,
            y2 = 0.4f
        )

        val mappedLabel = labelMapper.map(response)

        assertEquals(expectedLabel, mappedLabel)
    }

    @Test
    fun `map should use default values for missing coordinates`() {
        val response = LabelResponse(
            rus = "Test Label",
            eng = "",
            coord = listOf(0.1f, 0.2f)
        )
        val expectedLabel = Label(
            name = "Test Label",
            x1 = 0.1f,
            y1 = 0.2f,
            x2 = 0f,
            y2 = 0f
        )

        val mappedLabel = labelMapper.map(response)

        assertEquals(expectedLabel, mappedLabel)
    }

    @Test
    fun `map should handle empty coord list`() {
        val response = LabelResponse(
            rus = "Test Label",
            eng = "",
            coord = emptyList()
        )
        val expectedLabel = Label(
            name = "Test Label",
            x1 = 0f,
            y1 = 0f,
            x2 = 0f,
            y2 = 0f
        )

        val mappedLabel = labelMapper.map(response)

        assertEquals(expectedLabel, mappedLabel)
    }
}