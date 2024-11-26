package jp.co.yumemi.android.code_check.data.repository.http.common.message.response

/**
 * HTTPリクエストの結果として取得するレスポンスメッセージ
 * @param status ステータスコード
 * @param headers ヘッダー key: valueの関係
 * @param body メッセージボディ
 */
data class HttpResponseMessage<T>(
    val status: Int,
    val headers: List<Map<String, String>>,
    val body: T
)
