package jp.co.yumemi.android.code_check.data.http.github

import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.HttpResponse

/**
 * 4xxエラーに関する例外
 */
class ValidationFailedException(response: HttpResponse, cachedResponseText: String) :
    ResponseException(response = response, cachedResponseText = cachedResponseText)
