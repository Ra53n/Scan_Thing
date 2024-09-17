package ra53n.scan_thing

import android.net.Uri
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

interface Route {
    fun route(): String
}

object Main : Route {
    override fun route(): String = "main"
}

object ScanPhoto : Route {
    override fun route(): String = "scan_photo/{uri}"
    fun createRoute(uri: Uri): String {
        val encodedUri = URLEncoder.encode(
            uri.toString(),
            StandardCharsets.UTF_8.toString()
        )
        return "scan_photo/$encodedUri"
    }

    const val ARG_URI = "uri"
}