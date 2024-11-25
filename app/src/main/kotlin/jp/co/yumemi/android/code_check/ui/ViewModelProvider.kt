package jp.co.yumemi.android.code_check.ui

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import io.ktor.http.HeadersBuilder
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import jp.co.yumemi.android.code_check.data.http.RequestMessageBuilder
import jp.co.yumemi.android.code_check.data.http.github.GitHubHttpClientProvider
import jp.co.yumemi.android.code_check.data.http.github.RepositorySearchApi
import jp.co.yumemi.android.code_check.data.structure.github.RepositoryDetail
import jp.co.yumemi.android.code_check.ui.screen.repository_detail.RepositoryDetailViewModel
import jp.co.yumemi.android.code_check.ui.screen.repository_search.RepositorySearchViewModel

object ViewModelProvider {
    fun repositorySearch() = viewModelFactory {
        initializer {
            RepositorySearchViewModel(
                RepositorySearchApi(
                    clientProvider = GitHubHttpClientProvider,
                    requestMessageBuilder = RequestMessageBuilder(
                        urlProtocol = URLProtocol.HTTPS,
                        httpMethod = HttpMethod.Get,
                        host = "api.github.com",
                        headersBuilder = HeadersBuilder().let {
                            it.append(HttpHeaders.Accept, "application/vnd.github+json")
                            it
                        },
                        pathSegments = listOf("search", "repositories"),
                        defaultParameters = listOf()
                    )
                )
            )
        }
    }

    fun repositoryDetail(repositoryDetail: RepositoryDetail) = viewModelFactory {
        initializer {
            RepositoryDetailViewModel(repositoryDetail = repositoryDetail)
        }
    }
}
