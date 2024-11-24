package jp.co.yumemi.android.code_check.data.http

import io.ktor.client.HttpClient

interface ClientProvider {
    fun getClient(): HttpClient
    fun close()
}
