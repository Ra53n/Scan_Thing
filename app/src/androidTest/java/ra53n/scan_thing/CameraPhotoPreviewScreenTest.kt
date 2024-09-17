package ra53n.scan_thing

import android.Manifest
import androidx.camera.core.ImageCapture
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ra53n.feature_main.presentation.CameraPhotoPreviewScreen

@RunWith(AndroidJUnit4::class)
class CameraPhotoPreviewScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val grantPermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(Manifest.permission.CAMERA)

    private lateinit var listener: (ImageCapture) -> Unit
    private var takePhotoWasCalled = false

    @Before
    fun setup() {
        takePhotoWasCalled = false
        listener = { takePhotoWasCalled = true }
    }

    @Test
    fun cameraPhotoPreviewScreen_displaysPreview() {
        composeTestRule.setContent {
            CameraPhotoPreviewScreen(listener)
        }

        composeTestRule.onNodeWithTag("CameraPreview").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Take picture").assertIsDisplayed()
    }

    @Test
    fun cameraPhotoPreviewScreen_triggersOnTakePhotoClick_whenCameraPermissionGranted() {
        composeTestRule.setContent {
            CameraPhotoPreviewScreen(listener)
        }

        composeTestRule.onNodeWithContentDescription("Take picture").performClick()

        Assert.assertTrue(takePhotoWasCalled)
    }
}