package jp.co.yumemi.android.code_check.data.repository.http.github.request.common

import jp.co.yumemi.android.code_check.data.repository.http.common.message.common.HttpHeader
import jp.co.yumemi.android.code_check.data.repository.http.common.message.common.HttpMethod
import jp.co.yumemi.android.code_check.data.repository.http.common.message.common.Url
import jp.co.yumemi.android.code_check.data.repository.http.common.message.common.UrlProtocol
import jp.co.yumemi.android.code_check.data.repository.http.common.message.request.HttpRequestMessage
import jp.co.yumemi.android.code_check.data.repository.http.common.message.request.builder.QueryMessageBuilder

/**
 * GitHub REST APIに関するHTTPリクエストメッセージを作るビルダーを集めたオブジェクト
 */
object GitHubMessageBuilder {
    private const val HOST = "api.github.com"

    /**
     * リポジトリーを検索するためのメッセージを作成するビルダー
     */
    val repositorySearch by lazy {
        QueryMessageBuilder(
            HttpRequestMessage(
                httpMethod = HttpMethod.Get,
                url = Url(
                    protocol = UrlProtocol.Https,
                    host = HOST,
                    pathSegments = listOf("search", "repositories"),
                    parameters = listOf()
                ),
                headers = mapOf(HttpHeader.Accept.value to "application/vnd.github+json")
            )
        )
    }
}
