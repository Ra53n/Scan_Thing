package ra53n.feature_photo

import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateFormatterUtilsTest {

    private val dateFormatterUtils = DateFormatterUtils()

    @Test
    fun `format should return date formatted with provided pattern and locale`() {
        val date = Date(1670000000000)
        val pattern = "yyyy-MM-dd HH:mm:ss"
        val locale = Locale.US
        val expectedFormattedDate = SimpleDateFormat(pattern, locale).format(date)

        val formattedDate = dateFormatterUtils.format(date.time, pattern, locale)

        Assert.assertEquals(expectedFormattedDate, formattedDate)
    }

    @Test
    fun `format should use default pattern and locale if not provided`() {
        val date = Date(1670000000000)
        val defaultPattern = "yyyy-MM-dd"
        val defaultLocale = Locale.getDefault()
        val expectedFormattedDate = SimpleDateFormat(defaultPattern, defaultLocale).format(date)

        val formattedDate = dateFormatterUtils.format(date.time)

        Assert.assertEquals(expectedFormattedDate, formattedDate)
    }

    @Test
    fun `format should handle different locales`() {
        val date = Date(1670000000000)
        val pattern = "MMMM d, yyyy"
        val locale = Locale.FRANCE
        val expectedFormattedDate = SimpleDateFormat(pattern, locale).format(date)

        val formattedDate = dateFormatterUtils.format(date.time, pattern, locale)

        Assert.assertEquals(expectedFormattedDate, formattedDate)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `format should throw exception for invalid pattern`() {
        val date = Date()
        val invalidPattern = "invalid_pattern"
        dateFormatterUtils.format(date.time, invalidPattern)
    }
}