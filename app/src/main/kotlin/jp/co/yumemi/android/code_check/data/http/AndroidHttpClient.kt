package jp.co.yumemi.android.code_check.data.http

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android

object AndroidHttpClient : ClientProvider {
    override val client: HttpClient by lazy {
        HttpClient(Android)
    }
}
