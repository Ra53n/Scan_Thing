package ra53n.feature_main.presentation

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ra53n.scan_thing.main.R

@Composable
fun PhotoPreviewScreen(
    photoUri: Uri,
    onRetryClick: () -> Unit,
    onScanClick: () -> Unit
) {

    BackHandler {
        onRetryClick()
    }

    Box(Modifier.fillMaxSize()) {
        if (photoUri != Uri.EMPTY) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                model = photoUri,
                contentDescription = "Photo"
            )
        }

        Row(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 96.dp, vertical = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                modifier = Modifier.padding(bottom = 30.dp),
                onClick = onRetryClick,
                content = {
                    Icon(
                        painter = painterResource(R.drawable.undo),
                        contentDescription = "Retry",
                        tint = Color.White,
                        modifier = Modifier
                            .size(100.dp)
                            .border(1.dp, Color.White, CircleShape)
                            .padding(4.dp)
                    )
                }
            )

            IconButton(
                modifier = Modifier.padding(bottom = 30.dp),
                onClick = onScanClick,
                content = {
                    Icon(
                        painter = painterResource(R.drawable.scan),
                        contentDescription = "Scan",
                        tint = Color.White,
                        modifier = Modifier
                            .size(100.dp)
                            .border(1.dp, Color.White, CircleShape)
                            .padding(4.dp)
                    )
                }
            )
        }
    }
}