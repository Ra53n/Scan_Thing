package ra53n.scan_thing.feature_scan_photo.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScanPhotoViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var scanPhotoController: ScanPhotoController

    override fun onCleared() {
        super.onCleared()
        scanPhotoController.clear()
    }
}