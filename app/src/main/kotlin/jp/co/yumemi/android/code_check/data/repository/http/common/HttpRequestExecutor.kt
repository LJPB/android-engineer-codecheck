package jp.co.yumemi.android.code_check.data.repository.http.common

import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import jp.co.yumemi.android.code_check.data.model.http.common.RequestMessage

/**
 * HTTPリクエストを実行するためのメソッドを持ったクラス
 */
object HttpRequestExecutor {
    /**
     * [requestMessage]をリクエストメッセージとして[client]を使ってリクエストを送りその結果を返す。
     * リクエスト後にcloseしないことに注意！
     * @param client HTTPリクエストを実行するためのクライアント
     * @param requestMessage リクエストに使うメッセージ
     * @return リクエストの結果
     */
    suspend fun getResponse(client: HttpClient, requestMessage: RequestMessage): HttpResponse =
        client.request {
            method = requestMessage.requestMethod
            url(requestMessage.requestUrlBuilder.build())
            headers { requestMessage.requestHeaders }
        }
}
