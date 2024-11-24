package jp.co.yumemi.android.code_check.data.http

import io.ktor.client.HttpClient

interface HttpClientProvider {
    fun getClient(): HttpClient
    fun close()
}
