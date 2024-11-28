package jp.co.yumemi.android.code_check.data.repository.http.ktor.client

import jp.co.yumemi.android.code_check.data.repository.http.github.request.common.GitHubMessageBuilder
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class KtorClientTest {
    private lateinit var ktorClient: KtorRequestClient
    private val requestMessage =
        GitHubMessageBuilder.repositorySearch.run {
            appendParameter("q" to "kotlin")
            build()
        }


    @Before
    fun setUp() {
        ktorClient = KtorRequestClient()
    }

    /**
     * リクエストを正しく送れるかのテスト
     */
    @Test
    fun request(): Unit = runBlocking {
        val response = ktorClient.request(requestMessage)
        println(response.headers)
        assert(true)
    }
}
