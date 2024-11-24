package jp.co.yumemi.android.code_check.data.http

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android

object AndroidHttpClientProvider : HttpClientProvider {
    private var client: HttpClient? = null

    @Synchronized
    override fun getClient(): HttpClient {
        if (client == null) {
            client = HttpClient(Android)
        }
        return client!!
    }

    override fun close() {
        client?.close()
        client = null
    }
}
