package jp.co.yumemi.android.code_check.data.repository.http.common

import io.ktor.http.HeadersBuilder
import io.ktor.http.HttpMethod
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import jp.co.yumemi.android.code_check.data.model.http.common.RequestMessage
import jp.co.yumemi.android.code_check.data.model.http.common.UrlParameter

/**
 * [RequestMessage]を作るためのクラス
 */
class RequestMessageBuilder(
    val urlProtocol: URLProtocol,
    val httpMethod: HttpMethod,
    val host: String,
    val headersBuilder: HeadersBuilder,
    val pathSegments: List<String>,
    val defaultParameters: List<UrlParameter>
) {
    /**
     * 追加するクエリパラメータのリスト
     */
    private var addParameters: MutableList<UrlParameter> = mutableListOf()

    /**
     * URLにクエリパラメータを追加する
     * [build]を呼ぶと追加したクエリパラメータはリセットされる
     */
    fun appendParameter(param: UrlParameter): RequestMessageBuilder {
        addParameters.add(param)
        return this
    }

    /**
     * [RequestMessage]を取得する
     * そのあとは[appendParameter]で追加したクエリパラメータをリセットする
     */
    fun build(): RequestMessage {
        val message = RequestMessage(
            requestMethod = httpMethod,
            requestUrlBuilder = createUrlBuilder(),
            requestHeaders = headersBuilder
        )
        initAddParameters()
        return message
    }

    /**
     * [appendParameter]で追加したクエリパラメータのリセット
     */
    private fun initAddParameters() {
        addParameters = mutableListOf()
    }

    /**
     * URLBuilderを作る
     */
    private fun createUrlBuilder(): URLBuilder {
        val urlBuilder = URLBuilder(
            protocol = urlProtocol,
            host = host,
            pathSegments = pathSegments
        ).run {
            defaultParameters.forEach { param ->
                parameters.append(param.key, param.value)
            }
            addParameters.forEach { param ->
                parameters.append(param.key, param.value)
            }
            this
        }
        return urlBuilder
    }
}
