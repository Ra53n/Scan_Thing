package ra53n.feature_main.presentation

import android.Manifest
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import ra53n.scan_thing.core.flux.Fail
import ra53n.scan_thing.core.flux.IComposableComponent
import ra53n.scan_thing.core.flux.Pending
import ra53n.scan_thing.core.flux.Success
import java.io.File
import javax.inject.Inject


class MainScreenComponent @Inject constructor(
    override val controller: MainController,
    private val outputDirectory: File,
    private val navigateToScanPhoto: (Uri) -> Unit
) : IComposableComponent<MainState, MainController> {

    @Composable
    override fun Content(state: State<MainState>) {
        MainScreen(
            state = state,
            controller = controller,
            outputDirectory = outputDirectory,
            navigateToScanPhoto = navigateToScanPhoto
        )
    }
}

@Composable
fun MainScreen(
    state: State<MainState>,
    controller: MainController,
    outputDirectory: File,
    navigateToScanPhoto: (Uri) -> Unit
) {
    val context = LocalContext.current

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        var permissionGranted = true
        permissions.entries.forEach {
            if (it.key in REQUIRED_PERMISSIONS && !it.value)
                permissionGranted = false
        }
        if (!permissionGranted) {
            Toast.makeText(
                context,
                "Permission request denied",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    LaunchedEffect(Unit) {
        cameraPermissionLauncher.launch(REQUIRED_PERMISSIONS)
    }

    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

            when (val photoState = state.value.photoState) {
                is Pending -> {
                    CameraPhotoPreviewScreen(
                        onTakePhotoClick = {
                            controller.dispatch(
                                MainAction.TakePhoto(
                                    outputDirectory = outputDirectory,
                                    imageCapture = it
                                )
                            )
                        }
                    )
                }

                is Success -> {
                    val uri = remember { photoState.result }
                    PhotoPreviewScreen(
                        photoUri = uri,
                        onScanClick = { navigateToScanPhoto(uri) },
                        onRetryClick = { controller.dispatch(MainAction.Back(uri)) }
                    )
                }

                is Fail -> {}
            }
        }
    }
}

val REQUIRED_PERMISSIONS =
    mutableListOf(
        Manifest.permission.CAMERA
    ).toTypedArray()
