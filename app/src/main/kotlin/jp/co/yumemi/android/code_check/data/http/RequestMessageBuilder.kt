package jp.co.yumemi.android.code_check.data.http

import io.ktor.http.HeadersBuilder
import io.ktor.http.HttpMethod
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import jp.co.yumemi.android.code_check.data.structure.http.RequestMessage
import jp.co.yumemi.android.code_check.data.structure.http.UrlParameter

/**
 * [RequestMessage]を作るためのクラス
 */
class RequestMessageBuilder(
    private val urlProtocol: URLProtocol,
    private val httpMethod: HttpMethod,
    private val host: String,
    private val headersBuilder: HeadersBuilder,
    private val pathSegments: List<String>,
    private val defaultParameters: List<UrlParameter>
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
        initTmp()
        return message
    }

    /**
     * [appendParameter]で追加したクエリパラメータのリセット
     */
    private fun initTmp() {
        addParameters = mutableListOf()
    }

    /**
     * URLBuilderを作る
     */
    private fun createUrlBuilder(): URLBuilder {
        val urlBuilder = URLBuilder(
            protocol = urlProtocol,
            host = host,
        ).run {
            if (pathSegments.isNotEmpty()) path(pathSegments)
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
