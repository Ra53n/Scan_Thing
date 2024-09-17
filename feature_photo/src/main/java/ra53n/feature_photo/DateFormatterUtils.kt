package ra53n.feature_photo

import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

open class DateFormatterUtils @Inject constructor() {

    open fun format(
        date: Long,
        pattern: String = "yyyy-MM-dd",
        locale: Locale = Locale.getDefault()
    ): String {
        val dateFormat = SimpleDateFormat(pattern, locale)
        return dateFormat.format(date)
    }
}