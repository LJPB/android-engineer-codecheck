package jp.co.yumemi.android.code_check.ui.screen.repository_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import jp.co.yumemi.android.code_check.data.http.github.GitHubStatusCodes
import jp.co.yumemi.android.code_check.data.http.github.exception.RateLimitException
import jp.co.yumemi.android.code_check.data.http.github.RepositorySearchApi
import jp.co.yumemi.android.code_check.data.http.github.exception.ValidationFailedException
import jp.co.yumemi.android.code_check.data.structure.github.RateLimitData
import jp.co.yumemi.android.code_check.data.structure.github.RepositorySearchResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RepositorySearchViewModel(
    private val repositorySearchApi: RepositorySearchApi
) : ViewModel() {

    private var rateLimitData: RateLimitData? = null

    private val _repositorySearchResult: MutableStateFlow<RepositorySearchResult> =
        MutableStateFlow(RepositorySearchResult(listOf()))

    val repositorySearchResult = _repositorySearchResult.asStateFlow()

    private val _searchWord: MutableStateFlow<String> = MutableStateFlow("")
    val searchWord = _searchWord.asStateFlow()

    /**
     * 単語でリポジトリを検索する
     */
    fun searchWithWord(word: String) = viewModelScope.launch {
        val result = runWithExceptionHandling { repositorySearchApi.searchWithWord(word) }
        _searchWord.update { word }
        _repositorySearchResult.update { result }
    }

    /**
     * 検索ワードの変更
     */
    fun changeSearchWord(query: String) = _searchWord.update { query }

    /**
     * 検索ワードのクリア
     */
    fun clearSearchWord() = _searchWord.update { "" }

    /**
     * HTTPリクエストと対応する例外処理を行い、検索結果([RepositorySearchResult])を返す
     */
    private suspend fun runWithExceptionHandling(httpRequest: suspend () -> HttpResponse): RepositorySearchResult =
        try {
            val response = httpRequest()
            val result: RepositorySearchResult = response.body()
            result.copy(status = GitHubStatusCodes.SUCCESS) // 検索に成功
        } catch (timeout: HttpRequestTimeoutException) { // タイムアウト
            RepositorySearchResult(
                repositories = listOf(),
                status = GitHubStatusCodes.TIME_OUT
            )
        } catch (rateLimitException: RateLimitException) { // レート制限
            rateLimitData = rateLimitException.getRateLimitDate() // レート制限の情報を取得
            RepositorySearchResult(
                repositories = listOf(),
                status = GitHubStatusCodes.RATE_LIMIT_STATUS_CODES
            )
        } catch (validationFailed: ValidationFailedException) { // 4xxエラー
            RepositorySearchResult(
                repositories = listOf(),
                status = GitHubStatusCodes.VALIDATION_FAILED
            )
        } catch (serverResponseException: ServerResponseException) { // 5xxエラー
            RepositorySearchResult(
                repositories = listOf(),
                status = GitHubStatusCodes.SERVER_RESPONSE_ERROR
            )
        }
}
