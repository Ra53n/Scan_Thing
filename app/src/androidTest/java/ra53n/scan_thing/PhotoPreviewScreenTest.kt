package ra53n.scan_thing

import android.net.Uri
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ra53n.feature_main.presentation.PhotoPreviewScreen

@RunWith(AndroidJUnit4::class)
class PhotoPreviewScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private val imageUri =
        Uri.parse("android.resource://${InstrumentationRegistry.getInstrumentation().targetContext.packageName}/${R.drawable.android}")
    private var retryWasClicked = false
    private var scanWasClicked = false
    private var mockOnRetryClick = { }
    private var mockOnScanClick = { }

    @Before
    fun setup() {
        retryWasClicked = false
        scanWasClicked = false
        mockOnRetryClick = {
            retryWasClicked = true
        }
        mockOnScanClick = {
            scanWasClicked = true
        }
    }

    @Test
    fun photoPreviewScreen_displaysPhoto_whenUriIsNotEmpty() {
        composeTestRule.setContent {
            PhotoPreviewScreen(
                photoUri = imageUri,
                onRetryClick = mockOnRetryClick,
                onScanClick = mockOnScanClick
            )
        }

        composeTestRule.onNodeWithContentDescription("Photo").assertIsDisplayed()
    }

    @Test
    fun photoPreviewScreen_doesNotDisplayPhoto_whenUriIsEmpty() {
        composeTestRule.setContent {
            PhotoPreviewScreen(
                photoUri = Uri.EMPTY,
                onRetryClick = mockOnRetryClick,
                onScanClick = mockOnScanClick
            )
        }

        composeTestRule.onNodeWithContentDescription("Photo").assertDoesNotExist()
    }

    @Test
    fun photoPreviewScreen_callsOnRetryClick_whenRetryButtonClicked() {
        composeTestRule.setContent {
            PhotoPreviewScreen(
                photoUri = imageUri,
                onRetryClick = mockOnRetryClick,
                onScanClick = mockOnScanClick
            )
        }

        composeTestRule.onNodeWithContentDescription("Retry").performClick()

        Assert.assertTrue(retryWasClicked)
    }

    @Test
    fun photoPreviewScreen_callsOnScanClick_whenScanButtonClicked() {
        composeTestRule.setContent {
            PhotoPreviewScreen(
                photoUri = imageUri,
                onRetryClick = mockOnRetryClick,
                onScanClick = mockOnScanClick
            )
        }

        composeTestRule.onNodeWithContentDescription("Scan").performClick()

        Assert.assertTrue(scanWasClicked)
    }
}