package ra53n.scan_thing.ui_kit.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color.LightGray,
    onPrimary = Color.White,
    secondary = Color.DarkGray,
    onSecondary = Color.White,
    background = Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = Color.LightGray,
    onPrimary = Color.White,
    secondary = Color.DarkGray,
    onSecondary = Color.White,
    background = Color.Black
)

@Composable
fun ScanThingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}