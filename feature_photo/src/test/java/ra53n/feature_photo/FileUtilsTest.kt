package ra53n.feature_photo

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.File

class FileUtilsTest {

    private lateinit var dateFormatterUtils: DateFormatterUtils
    private lateinit var fileUtils: FileUtils

    @Before
    fun setup() {
        dateFormatterUtils = mock()
        fileUtils = FileUtils(dateFormatterUtils)
    }

    @Test
    fun `createPhotoFile should create file with correct name and extension`() {
        val outputDirectory = File("testDir")
        val filenameFormat = "yyyyMMdd"
        val formattedDate = "20231120"
        whenever(
            dateFormatterUtils.format(
                date = any(),
                pattern = eq(filenameFormat),
                locale = any()
            )
        ).thenReturn(
            formattedDate
        )

        val createdFile = fileUtils.createPhotoFile(outputDirectory, filenameFormat)

        assertEquals("$formattedDate${FileUtils.DEFAULT_FILE_EXTENSION}", createdFile.name)
    }

    @Test
    fun `createPhotoFile should use default format and extension if not provided`() {
        val outputDirectory = File("testDir")
        val formattedDate = "2023-11-20-10-00-00-000"
        whenever(
            dateFormatterUtils.format(
                date = any(),
                pattern = eq(FileUtils.DEFAULT_FILE_NAME_FORMAT),
                locale = any()
            )
        ).thenReturn(formattedDate)

        val createdFile = fileUtils.createPhotoFile(outputDirectory)

        assertEquals("$formattedDate${FileUtils.DEFAULT_FILE_EXTENSION}", createdFile.name)
    }

    @Test
    fun `createPhotoFile should create file in correct directory`() {
        val outputDirectory = File("testDir")
        val formattedDate = "20231120"
        whenever(
            dateFormatterUtils.format(
                date = any(),
                pattern = any(),
                locale = any()
            )
        ).thenReturn(formattedDate)

        val createdFile = fileUtils.createPhotoFile(outputDirectory)

        assertEquals(outputDirectory, createdFile.parentFile)
    }

    @Test
    fun `createPhotoFile should pass correct timestamp to dateFormatterUtils`() {
        val outputDirectory = File("testDir")
        val filenameFormat = "yyyyMMdd"
        val currentTime = System.currentTimeMillis()
        whenever(
            dateFormatterUtils.format(
                date = eq(currentTime),
                pattern = eq(filenameFormat),
                locale = any()
            )
        ).thenReturn("")

        fileUtils.createPhotoFile(outputDirectory, filenameFormat)
    }
}