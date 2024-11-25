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
import io.ktor.serialization.kotlinx.json.json
import jp.co.yumemi.android.code_check.data.http.HttpClientProvider
import jp.co.yumemi.android.code_check.data.http.github.GitHubStatusCodes.RATE_LIMIT_STATUS_CODES
import jp.co.yumemi.android.code_check.data.http.github.GitHubStatusCodes.SERVER_RESPONSE_ERROR
import jp.co.yumemi.android.code_check.data.http.github.GitHubStatusCodes.VALIDATION_FAILED
import jp.co.yumemi.android.code_check.data.http.github.exception.RateLimitException
import jp.co.yumemi.android.code_check.data.http.github.exception.ValidationFailedException
import kotlinx.serialization.json.Json

object GitHubHttpClientProvider : HttpClientProvider {
    private var client: HttpClient? = null
    private const val TIME_OUT_MILLS = 5000L // 5秒でタイムアウト

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
                            in VALIDATION_FAILED -> throw ValidationFailedException(
                                exceptionResponse,
                                exceptionResponseText
                            )

                            in SERVER_RESPONSE_ERROR -> throw ServerResponseException(
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
