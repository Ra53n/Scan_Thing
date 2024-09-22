package ra53n.scan_thing

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import ra53n.feature_main.presentation.MainScreenComponent
import ra53n.feature_main.presentation.MainViewModel
import ra53n.scan_thing.feature_scan_photo.presentation.ScanPhotoScreenComponent
import ra53n.scan_thing.feature_scan_photo.presentation.ScanPhotoViewModel
import ra53n.scan_thing.ui_kit.theme.ScanThingTheme
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val outputDirectory by lazy { initOutputDirectory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.Black.toArgb()),
            navigationBarStyle = SystemBarStyle.dark(Color.Black.toArgb())
        )
        setContent {
            val navController = rememberNavController()
            ScanThingTheme {
                NavHost(navController = navController, startDestination = Main.route()) {
                    composable(Main.route()) {
                        val viewModel = hiltViewModel<MainViewModel>()
                        MainScreenComponent(
                            controller = viewModel.mainController,
                            outputDirectory = outputDirectory,
                            navigateToScanPhoto = { uri ->
                                navController.navigate(ScanPhoto.createRoute(uri))
                            }
                        ).invoke()
                    }

                    composable(
                        route = ScanPhoto.route(),
                        arguments = listOf(navArgument(ScanPhoto.ARG_URI) {
                            type = NavType.StringType
                        })
                    ) { backStackEntry ->
                        val viewModel = hiltViewModel<ScanPhotoViewModel>()
                        val uri = backStackEntry.arguments?.getString(ScanPhoto.ARG_URI)
                        ScanPhotoScreenComponent(
                            controller = viewModel.scanPhotoController,
                            photoUri = Uri.parse(uri)
                        ).invoke()
                    }
                }
            }
        }
    }

    private fun initOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }
}
