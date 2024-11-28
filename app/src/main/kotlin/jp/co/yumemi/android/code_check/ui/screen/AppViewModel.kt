package jp.co.yumemi.android.code_check.ui.screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {
    private val _networkState = MutableStateFlow(true)
    val networkState = _networkState.asStateFlow()

    fun changeNetworkState(isActive: Boolean) = _networkState.update { isActive }
}
