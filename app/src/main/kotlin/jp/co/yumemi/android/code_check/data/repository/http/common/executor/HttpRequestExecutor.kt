package jp.co.yumemi.android.code_check.data.repository.http.common.executor

import jp.co.yumemi.android.code_check.data.repository.http.common.message.request.HttpRequestMessage
import jp.co.yumemi.android.code_check.data.repository.http.common.message.response.HttpResponseMessage

/**
 * HTTP通信のリクエストを行う
 */
interface HttpRequestExecutor {
    /**
     * 渡された[requestMessage]でHTTPリクエストを行い、その結果を[HttpResponseMessage]で返す
     * @param requestMessage 実行したいリクエストメッセージ
     * @return レスポンスの結果。レスポンスボディはStringで格納される。
     */
    suspend fun request(requestMessage: HttpRequestMessage): HttpResponseMessage<String>
}
