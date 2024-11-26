package jp.co.yumemi.android.code_check.data.repository.http.common.executor.ktor

import jp.co.yumemi.android.code_check.data.repository.http.github.GitHubHttpClientProvider
import jp.co.yumemi.android.code_check.data.repository.http.github.request.GitHubMessageBuilder
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class KtorExecutorTest {
    private lateinit var ktorExecutor: KtorExecutor
    private val requestMessage =
        GitHubMessageBuilder.repositorySearch.run {
            appendParameter("q" to "kotlin")
            build()
        }


    @Before
    fun setUp() {
        ktorExecutor = KtorExecutor(GitHubHttpClientProvider)
    }

    /**
     * リクエストを正しく送れるかのテスト
     */
    @Test
    fun request(): Unit = runBlocking {
        val response = ktorExecutor.request(requestMessage)
        println(response.headers)
        assert(true)
    }
}
