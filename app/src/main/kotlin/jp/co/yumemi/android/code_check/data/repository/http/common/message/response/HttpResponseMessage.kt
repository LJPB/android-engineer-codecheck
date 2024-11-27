package jp.co.yumemi.android.code_check.data.repository.http.common.message.response

import jp.co.yumemi.android.code_check.data.repository.http.common.message.common.HttpStatus

/**
 * HTTPリクエストの結果として取得するレスポンスメッセージ
 * @param status ステータスコード
 * @param headers ヘッダー key: valueの関係
 * @param body メッセージボディ
 */
data class HttpResponseMessage<T>(
    val status: Int = HttpStatus.INITIAL,
    val statusMessage: String = "",
    val headers: Map<String, List<String>> = mapOf(),
    val body: T
)
