package jp.co.yumemi.android.code_check.data.repository.http.common.message.request

import jp.co.yumemi.android.code_check.data.repository.http.common.message.common.HttpMethod
import jp.co.yumemi.android.code_check.data.repository.http.common.message.common.Url

/**
 * HTTPリクエストを送る際に必要な情報
 * @param httpMethod HTTPメソッドの種類
 * @param url 送信先のURL
 * @param headers 送信するヘッダー情報 key: valueの関係
 */
data class HttpRequestMessage(
    val httpMethod: HttpMethod,
    val url: Url,
    val headers: Map<String, String>
)
