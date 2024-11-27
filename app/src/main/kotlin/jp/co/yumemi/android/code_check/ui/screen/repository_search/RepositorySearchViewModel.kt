package jp.co.yumemi.android.code_check.ui.screen.repository_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.data.model.http.github.RateLimitData
import jp.co.yumemi.android.code_check.data.model.http.github.RepositorySearchResult
import jp.co.yumemi.android.code_check.data.repository.http.common.message.common.HttpStatus
import jp.co.yumemi.android.code_check.data.repository.http.common.message.response.HttpResponseMessage
import jp.co.yumemi.android.code_check.data.repository.http.github.request.repository_search.GitHubRepositorySearchService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositorySearchViewModel @Inject constructor(
    private val searchApi: GitHubRepositorySearchService
) : ViewModel() {

    private var rateLimitData: RateLimitData? = null

    private val _repositorySearchResult: MutableStateFlow<HttpResponseMessage<RepositorySearchResult>> =
        MutableStateFlow(
            HttpResponseMessage(
                status = HttpStatus.INITIAL,
                statusMessage = "",
                headers = mapOf(),
                body = RepositorySearchResult(repositories = listOf())
            )
        )

    val repositorySearchResult = _repositorySearchResult.asStateFlow()

    private val _searchWord: MutableStateFlow<String> = MutableStateFlow("")
    val searchWord = _searchWord.asStateFlow()

    /**
     * 単語でリポジトリを検索する
     */
    fun searchWithWord(word: String) = viewModelScope.launch {
        val result = searchApi.search(word)
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
}
