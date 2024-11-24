package jp.co.yumemi.android.code_check.data.http.github

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import jp.co.yumemi.android.code_check.data.http.HttpClientProvider
import kotlinx.serialization.json.Json

object GitHubHttpClientProvider : HttpClientProvider {
    private var client: HttpClient? = null
    private const val TIME_OUT_MILLS = 5000L // 5秒でタイムアウト

    // リポジトリー検索で発生しうるステータスコード
    // https://docs.github.com/ja/rest/search/search?apiVersion=2022-11-28#search-repositories--status-codes
    private val error422 = HttpStatusCode.fromValue(422)
    private val error503 = HttpStatusCode.fromValue(503)

    @Synchronized
    override fun getClient(): HttpClient {
        if (client == null) {
            client = HttpClient(Android) {
                install(ContentNegotiation) {
                    json(Json { ignoreUnknownKeys = true })
                }

                install(HttpTimeout) {
                    requestTimeoutMillis = TIME_OUT_MILLS
                }

                install(UserAgent) {
                    agent = "LJPB dev@ljpb.me"
                }

                // 2xx以外の応答に対する処理の設定
                expectSuccess = true
                HttpResponseValidator {
                    handleResponseExceptionWithRequest { exception, _ ->
                        val clientException = exception as? ClientRequestException
                            ?: return@handleResponseExceptionWithRequest
                        val exceptionResponse = clientException.response
                        val exceptionResponseText = exceptionResponse.bodyAsText()
                        when (exceptionResponse.status) {
                            error422 -> throw ValidationFailedException(
                                exceptionResponse,
                                exceptionResponseText
                            )

                            error503 -> throw ServerResponseException(
                                exceptionResponse,
                                exceptionResponseText
                            )

                            // レート制限
                            in RATE_LIMIT_STATUS_CODES -> throw RateLimitException(
                                exceptionResponse,
                                exceptionResponseText
                            )
                        }
                    }
                }
            }
        }
        return client!!
    }

    override fun close() {
        client?.close()
        client = null
    }
}
