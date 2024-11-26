package jp.co.yumemi.android.code_check.ui

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import jp.co.yumemi.android.code_check.data.repository.http.github.request.repository_search.GitHubRepositorySearchApi
import jp.co.yumemi.android.code_check.data.repository.http.ktor.client.GitHubHttpClientProvider
import jp.co.yumemi.android.code_check.data.repository.http.ktor.executor.KtorExecutor
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
