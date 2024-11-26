package jp.co.yumemi.android.code_check.data.repository.http.ktor.client

import io.ktor.client.HttpClient

interface HttpClientProvider {
    fun getClient(): HttpClient
    fun close()
}
