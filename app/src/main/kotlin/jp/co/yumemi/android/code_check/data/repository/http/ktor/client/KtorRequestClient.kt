package jp.co.yumemi.android.code_check.data.repository.http.ktor.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.UserAgent
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.HeadersBuilder
import io.ktor.http.Parameters
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.headers
import jp.co.yumemi.android.code_check.data.repository.http.common.client.HttpRequestClient
import jp.co.yumemi.android.code_check.data.repository.http.common.message.common.HttpMethod
import jp.co.yumemi.android.code_check.data.repository.http.common.message.common.HttpStatus
import jp.co.yumemi.android.code_check.data.repository.http.common.message.common.Url
import jp.co.yumemi.android.code_check.data.repository.http.common.message.common.UrlProtocol
import jp.co.yumemi.android.code_check.data.repository.http.common.message.request.HttpRequestMessage
import jp.co.yumemi.android.code_check.data.repository.http.common.message.response.HttpResponseMessage

/**
 * KtorのHTTPクライアントを管理する
 */
class KtorRequestClient : HttpRequestClient {
    private val timeOutMills = 5000L // 5秒でタイムアウト
    private var httpClient: HttpClient = getClient()

    /**
     * HTTPリクエストを実行する
     * @param requestMessage リクエストに関するリクエストメッセージ
     * @return リクエストのレスポンス
     */
    override suspend fun request(requestMessage: HttpRequestMessage): HttpResponseMessage<String> {
        // リクエスト用の情報
        val requestMethod = getKtorHttpMethod(requestMessage.httpMethod)
        val requestUrl = getKtorUrlBuilder(requestMessage.url).build()
        val requestHeaders = getKtorHeaders(requestMessage.headers)

        // レスポンスと対応するステータスコードの取得
        val (httpResponse, responseStatus) = runWithTryCatch {
            httpClient.request {
                method = requestMethod
                url(requestUrl)
                headers { requestHeaders.build() }
            }
        }

        val responseHeaders = httpResponse?.headers?.toMap() ?: emptyMap()
        val responseBody = httpResponse?.bodyAsText() ?: ""

        return HttpResponseMessage(
            status = responseStatus,
            headers = responseHeaders,
            body = responseBody
        )
    }

    /**
     * HTTPクライアントのセットアップ
     * 新しいHttpClientをセットする
     */
    override fun setUp() {
        close() // 既存のHttpClientがあればcloseする
        httpClient = getClient()
    }

    /**
     * HTTPクライアントのリソースの解放
     */
    override fun close() {
        httpClient.close()
    }

    private fun getClient(): HttpClient =
        HttpClient(Android) {
            install(HttpTimeout) {
                requestTimeoutMillis = timeOutMills
            }
            install(UserAgent) {
                agent = "dev@ljpb.me"
            }
        }

    /**
     * 例外処理と一緒にリクエストを行う
     * HttpResponseを得られたらそれとステータスコードを返し、取得できなければnullと対応するエラーコード([HttpStatus])を返す
     */
    private suspend fun runWithTryCatch(block: suspend () -> HttpResponse): Pair<HttpResponse?, Int> =
        try {
            val response = block()
            response to response.status.value
        } catch (responseException: ResponseException) {
            val response = responseException.response
            response to response.status.value
        } catch (timeoutException: HttpRequestTimeoutException) {
            null to HttpStatus.TIMEOUT
        } catch (exception: Exception) {
            null to HttpStatus.UNKNOWN
        }


    /**
     * カスタムHttpMethodをKtorで定義されているHttpMethodに変換する
     */
    private fun getKtorHttpMethod(httpMethod: HttpMethod): io.ktor.http.HttpMethod =
        when (httpMethod) {
            HttpMethod.Get -> io.ktor.http.HttpMethod.Get
            HttpMethod.Post -> io.ktor.http.HttpMethod.Post
            HttpMethod.Put -> io.ktor.http.HttpMethod.Put
        }

    /**
     * カスタムUrlProtocolをKtorで定義されているURLProtocolに変換する
     */
    private fun getKtorUrlProtocol(urlProtocol: UrlProtocol): URLProtocol =
        when (urlProtocol) {
            UrlProtocol.Https -> URLProtocol.HTTPS
            UrlProtocol.Http -> URLProtocol.HTTP
        }

    /**
     * カスタムUrlをKtorのURLBuilderに変換する
     */
    private fun getKtorUrlBuilder(url: Url): URLBuilder =
        URLBuilder(
            protocol = getKtorUrlProtocol(url.protocol),
            host = url.host,
            pathSegments = url.pathSegments,
            parameters = Parameters.build {
                url.parameters.forEach { param -> append(param.first, param.second) }
            }
        )

    /**
     * [headers]をKtorのHeaderBuilderに変換する
     */
    private fun getKtorHeaders(headers: Map<String, String>): HeadersBuilder =
        HeadersBuilder().let { builder ->
            headers.entries.forEach { map ->
                builder.append(map.key, map.value)
            }
            builder
        }

    /**
     * KtorによるレスポンスのヘッダーをMap<String, List<String>>に変換する
     */
    private fun Headers.toMap(): Map<String, List<String>> {
        val map: MutableMap<String, List<String>> = mutableMapOf()
        this.entries().forEach { entry ->
            map[entry.key] = entry.value
        }
        return map
    }
}
