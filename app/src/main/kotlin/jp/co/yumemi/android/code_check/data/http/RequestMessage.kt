package jp.co.yumemi.android.code_check.data.http

import io.ktor.http.HeadersBuilder
import io.ktor.http.HttpMethod
import io.ktor.http.URLBuilder

data class RequestMessage(
    val requestMethod: HttpMethod,
    val requestUrl: URLBuilder,
    val requestHeaders: HeadersBuilder
)
