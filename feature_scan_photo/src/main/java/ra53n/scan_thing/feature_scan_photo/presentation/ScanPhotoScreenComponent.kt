package ra53n.scan_thing.feature_scan_photo.presentation

import android.content.ContentResolver
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ra53n.scan_thing.core.flux.Fail
import ra53n.scan_thing.core.flux.IComposableComponent
import ra53n.scan_thing.core.flux.Pending
import ra53n.scan_thing.core.flux.Success
import ra53n.scan_thing.feature_scan_photo.R
import ra53n.scan_thing.feature_scan_photo.domain.Label
import ra53n.scan_thing.ui_kit.screens.ErrorScreen

class ScanPhotoScreenComponent(
    override val controller: ScanPhotoController,
    private val photoUri: Uri?
) : IComposableComponent<ScanPhotoState, ScanPhotoController> {

    @Composable
    override fun Content(state: State<ScanPhotoState>) {

        LaunchedEffect(Unit) {
            controller.dispatch(ScanPhotoAction.ScanPhoto(photoUri))
        }

        ScanPhotoScreen(
            controller = controller,
            state = state
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ScanPhotoScreen(
    controller: ScanPhotoController,
    state: State<ScanPhotoState>
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        when (val scanPhotoState = state.value.scanPhotoState) {
            is Success -> {
                val contentResolver = LocalContext.current.contentResolver
                val photoSize =
                    remember {
                        getPhotoSize(
                            uri = state.value.photoUri,
                            contentResolver = contentResolver
                        )
                    }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .scrollable(
                            state = rememberScrollState(0),
                            orientation = Orientation.Vertical
                        )
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .drawWithCache {
                                onDrawWithContent {
                                    drawContent()
                                    val selectedLabel = scanPhotoState.result?.labels?.getOrNull(
                                        state.value.selectedLabelIndex
                                    ) ?: return@onDrawWithContent

                                    drawDetectedObject(selectedLabel, photoSize)
                                }
                            },
                        contentScale = ContentScale.FillWidth,
                        model = state.value.photoUri,
                        contentDescription = "Photo"
                    )

                    if (scanPhotoState.result?.labels.isNullOrEmpty()) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 30.dp),
                            text = stringResource(R.string.objects_not_found),
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    } else {
                        FlowRow(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            scanPhotoState.result?.labels?.forEachIndexed { index, label ->
                                SelectableTextButton(
                                    text = label.name,
                                    isSelected = index == state.value.selectedLabelIndex,
                                    onClick = {
                                        controller.dispatch(
                                            ScanPhotoAction.UpdateSelectedLabel(
                                                index
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }

                }
            }

            is Pending -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Gray
                )
            }

            is Fail -> {
                ErrorScreen {
                    controller.dispatch(ScanPhotoAction.ScanPhoto(state.value.photoUri))
                }
            }
        }
    }
}

@Composable
fun SelectableTextButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Text(
        modifier = Modifier
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        text = text,
        color = if (isSelected) Color.Black else Color.White
    )
}

fun getPhotoSize(uri: Uri, contentResolver: ContentResolver): Pair<Int, Int>? {
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true

    return try {
        contentResolver.openInputStream(uri)?.use { inputStream ->
            BitmapFactory.decodeStream(inputStream, null, options)
            Pair(options.outHeight, options.outWidth)
        }
    } catch (e: Exception) {
        null
    }
}

private fun DrawScope.drawDetectedObject(label: Label, photoSize: Pair<Int, Int>?) {
    val widthCoefficient = if (photoSize != null) size.width / photoSize.first else 2f
    val heightCoefficient = if (photoSize != null) size.height / photoSize.second else 2f
    val left = label.x1 * widthCoefficient
    val top = label.y1 * heightCoefficient
    val right = label.x2 * widthCoefficient
    val bottom = label.y2 * heightCoefficient

    drawRect(
        color = Color.Green,
        topLeft = Offset(left, top),
        size = Size(right - left, bottom - top),
        style = Stroke(width = 5f)
    )
}
