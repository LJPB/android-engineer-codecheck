package jp.co.yumemi.android.code_check.data.repository.http.common.executor.ktor

import jp.co.yumemi.android.code_check.data.repository.http.github.request.GitHubMessageBuilder
import jp.co.yumemi.android.code_check.data.repository.http.ktor.client.GitHubHttpClientProvider
import jp.co.yumemi.android.code_check.data.repository.http.ktor.client.KtorClient
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class KtorClientTest {
    private lateinit var ktorClient: KtorClient
    private val requestMessage =
        GitHubMessageBuilder.repositorySearch.run {
            appendParameter("q" to "kotlin")
            build()
        }


    @Before
    fun setUp() {
        ktorClient = KtorClient(GitHubHttpClientProvider)
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
