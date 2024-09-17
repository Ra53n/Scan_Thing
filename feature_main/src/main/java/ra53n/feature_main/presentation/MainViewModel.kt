package ra53n.feature_main.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var mainController: MainController

    override fun onCleared() {
        super.onCleared()
        mainController.clear()
    }
}