package jp.co.yumemi.android.code_check.data.repository.http.github.request.repository_search

import jp.co.yumemi.android.code_check.data.repository.http.ktor.client.KtorRequestClient
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GitHubRepositorySearchApiTest {
    private lateinit var searchApi: GitHubRepositorySearchApi

    @Before
    fun setUp() {
        searchApi = GitHubRepositorySearchApi(
            KtorRequestClient()
        )
    }

    /**
     * 正しく検索できるかのテスト
     */
    @Test
    fun searchTest() = runBlocking {
        val response = searchApi.search("kotlin")
        assert(response.status == 200)
    }

    /**
     * クエリの指定ができるかのテスト
     */
    @Test
    fun perPageTest() = runBlocking {
        searchApi.prePage(1)
        val response = searchApi.search("kotlin")
        assert(response.body.responseBody.repositories.size == 1)
    }

    /**
     * 検索後に設定したクエリがリセットされているかのテスト
     */
    @Test
    fun queryClearTest() = runBlocking {
        searchApi.prePage(1)
        val beforeResponse = searchApi.search("kotlin")
        val afterResponse = searchApi.search("kotlin")
        assert(beforeResponse.body.responseBody.repositories.size != afterResponse.body.responseBody.repositories.size)
    }
}
