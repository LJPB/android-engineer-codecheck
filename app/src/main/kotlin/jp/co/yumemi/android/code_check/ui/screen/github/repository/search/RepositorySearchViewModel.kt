package jp.co.yumemi.android.code_check.ui.screen.github.repository.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.data.model.http.github.RepositorySearchResponse
import jp.co.yumemi.android.code_check.data.repository.http.common.message.common.HttpStatus
import jp.co.yumemi.android.code_check.data.repository.http.common.message.response.HttpResponseMessage
import jp.co.yumemi.android.code_check.data.repository.http.github.request.common.RateLimitStatusCodes
import jp.co.yumemi.android.code_check.data.repository.http.github.request.repository.GitHubRepositorySearchService
import jp.co.yumemi.android.code_check.ui.component.common.LoadingStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositorySearchViewModel @Inject constructor(
    private val searchApi: GitHubRepositorySearchService
) : ViewModel() {
    // 検索結果
    private val _repositorySearchResponse: MutableStateFlow<HttpResponseMessage<RepositorySearchResponse>> =
        MutableStateFlow(
            HttpResponseMessage(
                status = HttpStatus.INITIAL,
                statusMessage = "",
                headers = mapOf(),
                body = RepositorySearchResponse()
            )
        )

    val repositorySearchResponse = _repositorySearchResponse.asStateFlow()

    // 検索ワード
    private val _searchWord: MutableStateFlow<String> = MutableStateFlow("")
    val searchWord = _searchWord.asStateFlow()

    // 現在取得した最大のページ番号
    private val defaultPageNumber = 1
    private val _pageNumber = MutableStateFlow(defaultPageNumber)
    private val pageNumber = _pageNumber.asStateFlow()

    private val _loadingStatus = MutableStateFlow(LoadingStatus.Initial)
    val loadingStatus = _loadingStatus.asStateFlow()

    /**
     * 単語でリポジトリを検索する
     */
    fun searchWithWord(word: String) = viewModelScope.launch {
        _repositorySearchResponse.update { // 読み込み中に設定
            HttpResponseMessage(
                status = HttpStatus.LOADING,
                statusMessage = "",
                headers = mapOf(),
                body = RepositorySearchResponse()
            )
        }
        _pageNumber.update { defaultPageNumber } // 新規検索だからページ番号をリセット
        _loadingStatus.update { LoadingStatus.Initial }  // 新規検索だから追加読み込み状況をリセット
        val result = searchApi.search(word)
        _repositorySearchResponse.update { result }
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
     * ページの追加読み込み
     */
    fun loadNextPage() = viewModelScope.launch {
        _loadingStatus.update { LoadingStatus.Loading }
        _pageNumber.update { it + 1 }
        val result = searchApi.let {
            it.pageOf(pageNumber.value)
            it.search(repositorySearchResponse.value.body.searchWord)
        }
        when (result.status) {
            HttpStatus.SUCCESS -> {
                // すでに検索済みのリポジトリーのリスト
                val oldRepositories = repositorySearchResponse.value.body.responseBody.repositories

                // 新しく取得したリポジトリーのリスト
                val resultRepositories = result.body.responseBody.repositories

                // 追加読み込み後のリポジトリーのリスト(旧リスト+新リスト)
                // このリストをUIで表示したい
                val newRepositories = oldRepositories + resultRepositories

                // 追加読み込み後のRepositorySearchResult(検索結果。リポジトリーのリストなどが含まれる)
                // 追加読み込みの結果からリポジトリーのリストをnewRepositoriesに置き換えている
                // 言い換えると、すでに取得してある検索結果に対して、そのリポジトリーのリストに新しく取得したリポジトリーのリストを加える作業
                val newRepositorySearchResult =
                    result.body.responseBody.copy(repositories = newRepositories)

                // 追加読み込み後のRepositorySearchResponse(検索結果のボディ(RepositorySearchResult)や次のページの情報などが含まれる)
                val newRepositorySearchResponse =
                    result.body.copy(responseBody = newRepositorySearchResult)

                _repositorySearchResponse.update { result.copy(body = newRepositorySearchResponse) }
                _loadingStatus.update { LoadingStatus.Success }
            }

            in RateLimitStatusCodes -> {
                // レート制限の時はレート制限画面に移動したいから、_repositorySearchResponseを書き換える
                _repositorySearchResponse.update { result }
                _loadingStatus.update { LoadingStatus.Failed }
            }

            // その他エラーの時はすでに取得してある検索結果は表示したままにしたいから
            // _repositorySearchResponseは書き換えない
            else -> _loadingStatus.update { LoadingStatus.Failed }
        }
    }
}
