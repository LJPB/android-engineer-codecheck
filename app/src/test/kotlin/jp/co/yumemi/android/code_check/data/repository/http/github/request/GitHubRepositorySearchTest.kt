package jp.co.yumemi.android.code_check.data.repository.http.github.request

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import jp.co.yumemi.android.code_check.data.repository.http.github.request.repository_search.GitHubRepositorySearchApi
import jp.co.yumemi.android.code_check.data.repository.http.ktor.client.HttpClientProvider
import jp.co.yumemi.android.code_check.data.repository.http.ktor.client.KtorRequestClient
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GitHubRepositorySearchApiTest {
    private lateinit var searchApi: GitHubRepositorySearchApi

    @Before
    fun setUp() {
        searchApi = GitHubRepositorySearchApi(
            KtorRequestClient(
                object : HttpClientProvider {
                    private var client: HttpClient? = HttpClient(Android)
                    override fun getClient(): HttpClient {
                        if (client == null) client = HttpClient(Android)
                        return client!!
                    }

                    override fun close() {
                        client?.close()
                        client = null
                    }
                }
            )
        )
    }

    /**
     * 正しく検索できるかのテスト
     */
    @Test
    fun searchTest() = runBlocking {
        val response = searchApi.search("kotlin")
        response.body.repositories.forEach { println(it) }
        assert(response.status == 200)
    }

    /**
     * クエリの指定ができるかのテスト
     */
    @Test
    fun perPageTest() = runBlocking {
        searchApi.prePage(1)
        val response = searchApi.search("kotlin")
        response.body.repositories.forEach { println(it) }
        assert(response.body.repositories.size == 1)
    }

    /**
     * 検索後に設定したクエリがリセットされているかのテスト
     */
    @Test
    fun queryClearTest() = runBlocking {
        searchApi.prePage(1)
        val beforeResponse = searchApi.search("kotlin")
        val afterResponse = searchApi.search("kotlin")
        assert(beforeResponse.body.repositories.size != afterResponse.body.repositories.size)
    }
}
