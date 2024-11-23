package jp.co.yumemi.android.code_check.data.http

import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.utils.io.core.use

/**
 * HTTPリクエストを実行するためのメソッドを持ったクラス
 */
object HttpRequestExecutor {
    /**
     * [requestMessage]をリクエストメッセージとして[client]を使ってリクエストを送りその結果を返す。
     * リクエスト後に自動でclientをcloseする。
     * @param client HTTPリクエストを実行するためのクライアント
     * @param requestMessage リクエストに使うメッセージ
     * @return リクエストの結果
     */
    suspend fun getResponse(client: HttpClient, requestMessage: RequestMessage): HttpResponse =
        client.use {
            it.request {
                method = requestMessage.requestMethod
                url(requestMessage.requestUrl.build())
                headers { requestMessage.requestHeaders }
            }
        }
}
