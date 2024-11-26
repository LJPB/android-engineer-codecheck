package jp.co.yumemi.android.code_check.data.repository.http.ktor.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.UserAgent

object GitHubHttpClientProvider : HttpClientProvider {
    private var client: HttpClient? = null
    private const val TIME_OUT_MILLS = 5000L // 5秒でタイムアウト

    @Synchronized
    override fun getClient(): HttpClient {
        if (client == null) {
            client = HttpClient(Android) {
                install(HttpTimeout) {
                    requestTimeoutMillis = TIME_OUT_MILLS
                }
                install(UserAgent) {
                    agent = "LJPB dev@ljpb.me"
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
