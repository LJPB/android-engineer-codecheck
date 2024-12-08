package jp.co.yumemi.android.code_check.data.repository.http.github.request.repository

import jp.co.yumemi.android.code_check.data.model.http.github.RepositorySearchResponse
import jp.co.yumemi.android.code_check.data.model.http.github.RepositorySearchResult
import jp.co.yumemi.android.code_check.data.repository.http.common.executor.HttpRequestExecutor
import jp.co.yumemi.android.code_check.data.repository.http.common.message.common.HttpStatus
import jp.co.yumemi.android.code_check.data.repository.http.common.message.request.HttpRequest
import jp.co.yumemi.android.code_check.data.repository.http.common.message.response.HttpResponseMessage
import jp.co.yumemi.android.code_check.data.repository.http.github.request.common.GitHubMessageBuilder
import jp.co.yumemi.android.code_check.data.repository.http.github.request.common.RateLimitParser
import jp.co.yumemi.android.code_check.data.util.LinkHeaderParser
import kotlinx.serialization.json.Json
import javax.inject.Inject


/**
 * GitHub REST APIでリポジトリーを検索するためのAPI
 */
class GitHubRepositorySearchApi @Inject constructor(executor: HttpRequestExecutor) :
    HttpRequest(executor), GitHubRepositorySearchService {
    private val messageBuilder = GitHubMessageBuilder.repositorySearch
    private var sort: RepositorySearchQueryType.Sort? = null
    private var order: RepositorySearchQueryType.Order? = null

    /**
     * 検索する
     * @param word 検索ワード
     * 検索結果(リポジトリー、HTTPレスポンスボディ)を[RepositorySearchResult]にパースして取得する
     * 何からの理由で検索結果を取得できなかった場合、[RepositorySearchResult]の中身は空となり、対応するステータスコードが[HttpResponseMessage]に設定される
     */
    override suspend fun search(word: String): HttpResponseMessage<RepositorySearchResponse> {
        messageBuilder.appendParameter(RepositorySearchQueryType.SearchWord.KEY to word)

        val response = httpRequestExecutor.request(messageBuilder.build())

        // 追加したクエリを初期化する
        // クエリの追加はすなわち検索条件の設定であり、それはUI操作によって行われるから、その情報はViewModelで保持する
        messageBuilder.clear()

        // 検索結果をJsonからRepositorySearchResultにパース
        val repositorySearchResult =
            if (response.body.isNotEmpty() && response.status == HttpStatus.SUCCESS) {
                val json = Json {
                    ignoreUnknownKeys = true
                }
                json.decodeFromString<RepositorySearchResult>(response.body)
            } else {
                // レスポンスボディを取得できなければ空のRepositorySearchResultを検索結果として設定する
                RepositorySearchResult(repositories = listOf())
            }

        return HttpResponseMessage(
            status = response.status,
            statusMessage = response.statusMessage,
            body = RepositorySearchResponse(
                searchWord = word,
                sort = sort,
                order = order,
                rateLimitData = RateLimitParser.getRateLimitData(response.headers),
                hasNextPage = hasNextPage(
                    response.headers["link"]?.joinToString(separator = "") ?: ""
                ),
                responseBody = repositorySearchResult
            ),
            headers = response.headers
        )
    }

    override fun sortBy(type: RepositorySearchQueryType.Sort) {
        sort = type
        messageBuilder.appendParameter(type.key to type.value)
    }

    override fun orderBy(type: RepositorySearchQueryType.Order) {
        order = type
        messageBuilder.appendParameter(type.key to type.value)
    }

    override fun prePage(number: Int) {
        messageBuilder.appendParameter(RepositorySearchQueryType.PerPage.KEY to number.toString())
    }

    override fun pageOf(number: Int) {
        messageBuilder.appendParameter(RepositorySearchQueryType.Page.KEY to number.toString())
    }

    private fun hasNextPage(linkHeaderString: String): Boolean =
        LinkHeaderParser.getLink(linkHeaderString, "next") != null
}
