package jp.co.yumemi.android.code_check.ui

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import jp.co.yumemi.android.code_check.data.repository.http.common.executor.ktor.KtorExecutor
import jp.co.yumemi.android.code_check.data.repository.http.github.GitHubHttpClientProvider
import jp.co.yumemi.android.code_check.data.repository.http.github.request.repository_search.GitHubRepositorySearchApi
import jp.co.yumemi.android.code_check.ui.screen.repository_search.RepositorySearchViewModel

object ViewModelProvider {
    fun repositorySearch() = viewModelFactory {
        initializer {
            RepositorySearchViewModel(
                GitHubRepositorySearchApi(
                    KtorExecutor(
                        GitHubHttpClientProvider
                    )
                )
            )
        }
    }
}
