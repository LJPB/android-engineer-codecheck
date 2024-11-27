package jp.co.yumemi.android.code_check.data.repository.http.common.executor

import jp.co.yumemi.android.code_check.data.repository.http.common.message.request.HttpRequestMessage
import jp.co.yumemi.android.code_check.data.repository.http.common.message.response.HttpResponseMessage

/**
 * HTTPリクエストの実行メソッドを持たせる
 */
interface HttpRequestExecutor {
    suspend fun request(requestMessage: HttpRequestMessage): HttpResponseMessage<String>
}
