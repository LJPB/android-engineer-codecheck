package jp.co.yumemi.android.code_check.data.github.search

import io.ktor.http.HeadersBuilder
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import jp.co.yumemi.android.code_check.data.http.github.GitHubHttpClientProvider
import jp.co.yumemi.android.code_check.data.http.RequestMessageBuilder
import jp.co.yumemi.android.code_check.data.http.github.RepositorySearchApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RepositorySearchApiTest {
    private val clientProvider = GitHubHttpClientProvider
    private lateinit var requestMessageBuilder: RequestMessageBuilder
    private lateinit var repositorySearchApi: RepositorySearchApi

    @Before
    fun setUp() {
        requestMessageBuilder = RequestMessageBuilder(
            urlProtocol = URLProtocol.HTTPS,
            httpMethod = HttpMethod.Get,
            host = "api.github.com",
            headersBuilder = HeadersBuilder(),
            pathSegments = listOf("search", "repositories"),
            defaultParameters = listOf()
        )
        repositorySearchApi = RepositorySearchApi(
            clientProvider = clientProvider,
            requestMessageBuilder = requestMessageBuilder
        )
    }

    /**
     * 検索キーワードからリソースをちゃんと取得できているかのテスト
     */
    @Test
    fun searchWithWordTest(): Unit = runBlocking {
        val response = repositorySearchApi.searchWithWord("kotlin")
        assert(response.status == HttpStatusCode.OK)
    }
    
    /**
     * URLからリソースをちゃんと取得できているかのテスト
     */
    @Test
    fun getResponseFromUrlTest(): Unit = runBlocking {
        val response = repositorySearchApi.getResponseFromUrl("https://api.github.com/search/repositories?q=kotlin")
        assert(response.status == HttpStatusCode.OK)
    }
}
