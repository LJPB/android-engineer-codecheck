package jp.co.yumemi.android.code_check.data.repository.http.github

import io.ktor.http.HttpStatusCode

/**
 * GitHub REST APIの応答ステータスコード
 */
object GitHubStatusCodes {
    /**
     * レート制限時に発生するステータスコードのリスト
     * [参考](https://docs.github.com/ja/rest/search/search?apiVersion=2022-11-28#search-repositories--status-codes)
     */
    val RATE_LIMIT_STATUS_CODES = setOf(
        HttpStatusCode.fromValue(403),
        HttpStatusCode.fromValue(429)
    )

    /**
     * リポジトリー検索で発生しうるステータスコード 4xx
     * [参考](https://docs.github.com/ja/rest/search/search?apiVersion=2022-11-28#search-repositories--status-codes)
     */
    val VALIDATION_FAILED = setOf(HttpStatusCode.fromValue(422))

    /**
     * リポジトリー検索で発生しうるステータスコード 5xx
     * [参考](https://docs.github.com/ja/rest/search/search?apiVersion=2022-11-28#search-repositories--status-codes)
     */
    val SERVER_RESPONSE_ERROR = setOf(HttpStatusCode.fromValue(503))

    /**
     * 検索に成功した時のステータスコード 200 [参考](https://docs.github.com/ja/rest/search/search?apiVersion=2022-11-28#search-repositories--status-codes)
     */
    val SUCCESS = setOf(HttpStatusCode.fromValue(200))

    /**
     * タイムアウトを表す
     */
    val TIME_OUT: Set<HttpStatusCode> = emptySet()
}
