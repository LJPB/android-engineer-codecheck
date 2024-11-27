package jp.co.yumemi.android.code_check.data.repository.http.common.message.request.builder

import jp.co.yumemi.android.code_check.data.repository.http.common.message.common.HttpMethod
import jp.co.yumemi.android.code_check.data.repository.http.common.message.common.Url
import jp.co.yumemi.android.code_check.data.repository.http.common.message.common.UrlProtocol
import jp.co.yumemi.android.code_check.data.repository.http.common.message.request.HttpRequestMessage
import org.junit.Assert.assertEquals
import org.junit.Test

class QueryMessageBuilderTest {
    private val defaultMessage = HttpRequestMessage(
        httpMethod = HttpMethod.Get,
        url = Url(
            protocol = UrlProtocol.Https,
            host = "api.github.com",
            parameters = listOf(),
            pathSegments = listOf()
        ),
        headers = mapOf()
    )
    private val messageBuilder = QueryMessageBuilder(defaultMessage)

    /**
     * パラメータをちゃんと追加できているかのテスト
     */
    @Test
    fun appendTest() {
        val expectParameters = listOf(
            "q" to "kotlin",
            "order" to "desc"
        )
        messageBuilder.appendParameter(expectParameters[0])
        messageBuilder.appendParameter(expectParameters[1])
        val message = messageBuilder.build()
        val actualParameters = message.url.parameters
        assertEquals(expectParameters, actualParameters)
    }

    /**
     * クリアを呼べば追加したパラメータがクリアされるかのテスト
     */
    @Test
    fun clearTest() {
        messageBuilder.appendParameter("q" to "kotlin")
        messageBuilder.clear()
        val message = messageBuilder.build()
        val actualParameters = message.url.parameters
        assert(actualParameters.isEmpty())
    }

    /**
     * パラメータをトリガーとして、パラメータを別のパラメータにちゃんと置き換えられるかのテスト
     */
    @Test
    fun replaceParameterByParameter() {
        val beforeParam = "q" to "kotlin"
        val afterParam = "order" to "desc"
        messageBuilder.appendParameter(beforeParam)
        messageBuilder.replaceParameter(beforeParam, afterParam)
        val message = messageBuilder.build()
        val actualParameters = message.url.parameters
        assertEquals(actualParameters, listOf(afterParam))
    }

    /**
     * キーをトリガーとして、パラメータを別のパラメータにちゃんと置き換えられるかのテスト
     */
    @Test
    fun replaceParameterByKey() {
        val key = "q"
        val beforeValue = "kotlin"
        val afterValue = "flutter"
        messageBuilder.appendParameter(key to beforeValue)
        messageBuilder.replaceParameter(key, afterValue)
        val message = messageBuilder.build()
        val actualParameters = message.url.parameters
        assertEquals(actualParameters, listOf(key to afterValue))
    }

    /**
     * パラメータをトリガーとして対象パラメータを削除できるかのテスト
     */
    @Test
    fun removeParameterByParameter() {
        val param = "q" to "kotlin"
        val removeParam = "order" to "desc"
        messageBuilder.appendParameter(param)
        messageBuilder.appendParameter(removeParam)
        messageBuilder.removeParameter(removeParam)
        val message = messageBuilder.build()
        val actualParameters = message.url.parameters
        assertEquals(actualParameters, listOf(param))
    }

    /**
     * キーをトリガーとして、対象パラメータを削除できるかのテスト
     */
    @Test
    fun removeParameterByKey() {
        val param = "q" to "kotlin"
        val removeKey = "sort"
        val removeParam1 = removeKey to "forks"
        val removeParam2 = removeKey to "stars"
        messageBuilder.appendParameter(param)
        messageBuilder.appendParameter(removeParam1)
        messageBuilder.appendParameter(removeParam2)
        messageBuilder.removeParameter(removeKey)
        val message = messageBuilder.build()
        val actualParameters = message.url.parameters
        assertEquals(actualParameters, listOf(param))
    }
}
