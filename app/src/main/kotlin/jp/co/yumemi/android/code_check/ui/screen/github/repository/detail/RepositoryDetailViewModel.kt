package jp.co.yumemi.android.code_check.ui.screen.github.repository.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import jp.co.yumemi.android.code_check.data.model.http.github.RepositoryDetail
import kotlin.reflect.typeOf

class RepositoryDetailViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val navArg = savedStateHandle.toRoute<RepositoryDetailDestination>(
        typeMap = mapOf(typeOf<RepositoryDetail>() to RepositoryDetailNavType)
    )
    val repositoryDetail = navArg.detail
}
