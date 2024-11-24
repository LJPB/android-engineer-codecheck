package jp.co.yumemi.android.code_check.data.structure.github

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * リポジトリの検索結果を格納するデータクラス
 * @param status 検索結果のステータス GitHubStatusCodesのプロパティを受け取る
 */
@Serializable
data class RepositorySearchResult(
    @SerialName("items")
    val repositories: List<RepositoryItem>,

    @Transient
    var status: Set<HttpStatusCode> = emptySet()
)

/**
 * 個々のリポジトリを表すデータクラス
 */
@Serializable
data class RepositoryItem(
    @SerialName("full_name")
    val fullName: String,

    @SerialName("owner")
    val owner: RepositoryOwner,

    @SerialName("language")
    val language: String?,

    @SerialName("stargazers_count")
    val stargazersCount: Int,

    @SerialName("watchers_count")
    val watchersCount: Int,

    @SerialName("forks_count")
    val forksCount: Int,

    @SerialName("open_issues_count")
    val openIssuesCount: Int,
)

/**
 * リポジトリのownerを表すデータクラス
 */
@Serializable
data class RepositoryOwner(
    @SerialName("avatar_url")
    val avatarUrl: String
)
