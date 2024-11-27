package jp.co.yumemi.android.code_check.data.repository.http.github.request.repository_search

import jp.co.yumemi.android.code_check.data.model.http.github.RepositorySearchResult
import jp.co.yumemi.android.code_check.data.repository.http.common.client.HttpRequestClient
import jp.co.yumemi.android.code_check.data.repository.http.common.message.response.HttpResponseMessage
import jp.co.yumemi.android.code_check.data.repository.http.github.request.GitHubMessageBuilder
import kotlinx.serialization.json.Json
import javax.inject.Inject


/**
 * GitHub REST APIでリポジトリーを検索するためのAPI
 * @param httpRequestClient HTTPリクエストを実行するためのクラス
 */
class GitHubRepositorySearchApi @Inject constructor(private val httpRequestClient: HttpRequestClient) :
    GitHubRepositorySearchService {
    private val messageBuilder = GitHubMessageBuilder.repositorySearch

    /**
     * 検索する
     * @param word 検索ワード
     * 検索結果(リポジトリー、HTTPレスポンスボディ)を[RepositorySearchResult]にパースして取得する
     * 何からの理由で検索結果を取得できなかった場合、[RepositorySearchResult]の中身は空となり、対応するステータスコードが[HttpResponseMessage]に設定される
     */
    override suspend fun search(word: String): HttpResponseMessage<RepositorySearchResult> {
        messageBuilder.appendParameter(RepositorySearchQueryType.SearchWord.KEY to word)

        val response = httpRequestClient.request(messageBuilder.build())

        // 追加したクエリを初期化する
        // クエリの追加はすなわち検索条件の設定であり、それはUI操作によって行われるから、その情報はViewModelで保持する
        messageBuilder.clear()

        // 検索結果をJsonからRepositorySearchResultにパース
        val repositorySearchResult =
            if (response.body.isNotEmpty() && response.status == 200) { // ハードコードは要修正！
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
            body = repositorySearchResult,
            headers = response.headers
        )
    }

    override fun sortBy(type: RepositorySearchQueryType.Sort) {
        messageBuilder.appendParameter(type.key to type.value)
    }

    override fun orderBy(type: RepositorySearchQueryType.Order) {
        messageBuilder.appendParameter(type.key to type.value)
    }

    override fun prePage(number: Int) {
        messageBuilder.appendParameter(RepositorySearchQueryType.PerPage.KEY to number.toString())
    }

    override fun pageOf(number: Int) {
        messageBuilder.appendParameter(RepositorySearchQueryType.Page.KEY to number.toString())
    }
}
