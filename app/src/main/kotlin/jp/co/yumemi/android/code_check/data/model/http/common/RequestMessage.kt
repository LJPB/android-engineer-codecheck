package jp.co.yumemi.android.code_check.data.model.http.common

import io.ktor.http.HeadersBuilder
import io.ktor.http.HttpMethod
import io.ktor.http.URLBuilder

/**
 * HTTP通信に必要なリクエストメッセージ群
 */
data class RequestMessage(
    val requestMethod: HttpMethod,
    val requestUrlBuilder: URLBuilder,
    val requestHeaders: HeadersBuilder
)
