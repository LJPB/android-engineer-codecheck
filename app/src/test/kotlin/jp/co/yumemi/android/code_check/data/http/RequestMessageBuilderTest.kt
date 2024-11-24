package jp.co.yumemi.android.code_check.data.http

import io.ktor.http.HeadersBuilder
import io.ktor.http.HttpMethod
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.path
import jp.co.yumemi.android.code_check.data.structure.http.RequestMessage
import jp.co.yumemi.android.code_check.data.structure.http.UrlParameter
import junit.framework.TestCase.assertEquals
import org.junit.Test

class RequestMessageBuilderTest {
    private val initialPath = "repository"
    private val initialParam = UrlParameter("q", "kotlin")

    private val urlProtocol = URLProtocol.HTTPS
    private val httpMethod = HttpMethod.Get
    private val host = "api.github.com"
    private val headersBuilder = HeadersBuilder()
    private val pathSegments = listOf(initialPath)
    private val defaultParameters = listOf(initialParam)

    private val requestMessageBuilder = RequestMessageBuilder(
        urlProtocol = urlProtocol,
        httpMethod = httpMethod,
        host = host,
        headersBuilder = headersBuilder,
        pathSegments = pathSegments,
        defaultParameters = defaultParameters
    )

    // テスト時に追加するクエリパラメータ
    private val addParameters = listOf(
        UrlParameter("111", "aaa"),
        UrlParameter("222", "ccc"),
        UrlParameter("333", "ddd"),
    )

    /**
     * RequestMessageを正しく作れるかのテスト
     */
    @Test
    fun defaultBuildTest() {
        val expectRequestMessage = RequestMessage(
            requestMethod = httpMethod,
            requestUrlBuilder = URLBuilder(
                protocol = urlProtocol,
                host = host,
                pathSegments = pathSegments
            ).run {
                defaultParameters.forEach { param -> parameters.append(param.key, param.value) }
                this
            },
            requestHeaders = headersBuilder
        )
        val resultRequestMessage = requestMessageBuilder.build()

        // リクエストメソッドの比較
        println("expectRequestMessage.requestMethod : ${expectRequestMessage.requestMethod}")
        println("resultRequestMessage.requestMethod : ${resultRequestMessage.requestMethod}")
        assertEquals(expectRequestMessage.requestMethod, resultRequestMessage.requestMethod)

        // URLBuilderの比較
        // そのまま渡してもうまく比較できないから、StringにしてURL文字列で比較している
        println("expectRequestMessage.requestUrlBuilder.build() : ${expectRequestMessage.requestUrlBuilder.build()}")
        println("ResultRequestMessage.requestUrlBuilder.build() : ${resultRequestMessage.requestUrlBuilder.build()}")
        assertEquals(
            expectRequestMessage.requestUrlBuilder.buildString(),
            resultRequestMessage.requestUrlBuilder.buildString()
        )

        // ヘッダーの比較
        println("expectRequestMessage.requestHeaders : ${expectRequestMessage.requestHeaders}")
        println("resultRequestMessage.requestHeaders : ${resultRequestMessage.requestHeaders}")
        assertEquals(expectRequestMessage.requestHeaders, resultRequestMessage.requestHeaders)
    }

    /**
     * クエリを正しく追加できるかのテスト
     */
    @Test
    fun buildTestWhenAppendParameters() {
        val resultRequestMessage = requestMessageBuilder
            .appendParameter(addParameters[0])
            .appendParameter(addParameters[1])
            .appendParameter(addParameters[2])
            .build()

        val expectRequestMessage = RequestMessage(
            requestMethod = httpMethod,
            requestUrlBuilder = URLBuilder(
                protocol = urlProtocol,
                host = host,
                pathSegments = pathSegments
            ).run {
                defaultParameters.forEach { param -> parameters.append(param.key, param.value) }
                addParameters.forEach { param -> parameters.append(param.key, param.value) }
                this
            },
            requestHeaders = headersBuilder
        )

        // リクエストメソッドの比較
        println("expectRequestMessage.requestMethod : ${expectRequestMessage.requestMethod}")
        println("resultRequestMessage.requestMethod : ${resultRequestMessage.requestMethod}")
        assertEquals(expectRequestMessage.requestMethod, resultRequestMessage.requestMethod)

        // URLBuilderの比較
        // そのまま渡してもうまく比較できないから、StringにしてURL文字列で比較している
        println("expectRequestMessage.requestUrlBuilder.build() : ${expectRequestMessage.requestUrlBuilder.build()}")
        println("ResultRequestMessage.requestUrlBuilder.build() : ${resultRequestMessage.requestUrlBuilder.build()}")
        assertEquals(
            expectRequestMessage.requestUrlBuilder.buildString(),
            resultRequestMessage.requestUrlBuilder.buildString()
        )

        // ヘッダーの比較
        println("expectRequestMessage.requestHeaders : ${expectRequestMessage.requestHeaders}")
        println("resultRequestMessage.requestHeaders : ${resultRequestMessage.requestHeaders}")
        assertEquals(expectRequestMessage.requestHeaders, resultRequestMessage.requestHeaders)
    }

    /**
     * クエリを追加した後にbuildをすると、追加したクエリはちゃんとリセットされているかのテスト
     */
    @Test
    fun buildTestAfterAppendedParameters() {
        requestMessageBuilder
            .appendParameter(addParameters[0])
            .appendParameter(addParameters[1])
            .appendParameter(addParameters[2])
            .build()

        val resultRequestMessage = requestMessageBuilder.build()
        val expectRequestMessage = RequestMessage(
            requestMethod = httpMethod,
            requestUrlBuilder = URLBuilder(
                protocol = urlProtocol,
                host = host,
                pathSegments = pathSegments
            ).run {
                defaultParameters.forEach { param -> parameters.append(param.key, param.value) }
                this
            },
            requestHeaders = headersBuilder
        )

        // リクエストメソッドの比較
        println("expectRequestMessage.requestMethod : ${expectRequestMessage.requestMethod}")
        println("resultRequestMessage.requestMethod : ${resultRequestMessage.requestMethod}")
        assertEquals(expectRequestMessage.requestMethod, resultRequestMessage.requestMethod)

        // URLBuilderの比較
        // そのまま渡してもうまく比較できないから、StringにしてURL文字列で比較している
        println("expectRequestMessage.requestUrlBuilder.build() : ${expectRequestMessage.requestUrlBuilder.build()}")
        println("ResultRequestMessage.requestUrlBuilder.build() : ${resultRequestMessage.requestUrlBuilder.build()}")
        assertEquals(
            expectRequestMessage.requestUrlBuilder.buildString(),
            resultRequestMessage.requestUrlBuilder.buildString()
        )

        // ヘッダーの比較
        println("expectRequestMessage.requestHeaders : ${expectRequestMessage.requestHeaders}")
        println("resultRequestMessage.requestHeaders : ${resultRequestMessage.requestHeaders}")
        assertEquals(expectRequestMessage.requestHeaders, resultRequestMessage.requestHeaders)
    }
}
