/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.content.Context
import androidx.lifecycle.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import jp.co.yumemi.android.code_check.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.data.RepositoryInfo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.util.Date
import kotlin.text.Typography.times

class OneViewModel(
    val context: Context
) : ViewModel() {

    // 検索結果
    fun searchResults(inputText: String): List<RepositoryInfo> = runBlocking {
        val client = HttpClient(Android)

        return@runBlocking GlobalScope.async {
            val response: HttpResponse = client?.get("https://api.github.com/search/repositories") {
                header("Accept", "application/vnd.github.v3+json")
                parameter("q", inputText)
            }

            val jsonBody = JSONObject(response.receive<String>())
            val jsonItems = jsonBody.optJSONArray("items")!!

            val repositoryInfoList = mutableListOf<RepositoryInfo>()

            repeat(jsonItems.length()) { count ->
                val jsonItem = jsonItems.optJSONObject(count)!!
                repositoryInfoList.add(jsonItem.toRepositoryInfo())
            }

            lastSearchDate = Date()

            return@async repositoryInfoList.toList()
        }.await()
    }

    private fun JSONObject.toRepositoryInfo(): RepositoryInfo {
        val name = this.optString("full_name")
        val ownerIconUrl = this.optJSONObject("owner")!!.optString("avatar_url")
        val language = this.optString("language")
        val stargazersCount = this.optLong("stargazers_count")
        val watchersCount = this.optLong("watchers_count")
        val forksCount = this.optLong("forks_conut")
        val openIssuesCount = this.optLong("open_issues_count")

        return RepositoryInfo(
            name = name,
            ownerIconUrl = ownerIconUrl,
            language = context.getString(R.string.written_language, language),
            stargazersCount = stargazersCount,
            watchersCount = watchersCount,
            forksCount = forksCount,
            openIssuesCount = openIssuesCount
        )
    }
}