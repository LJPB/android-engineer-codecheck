package jp.co.yumemi.android.code_check.data.structure.github

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * リポジトリの検索結果を格納するデータクラス
 */
@Serializable
data class RepositorySearchResult(
    @SerialName("items")
    val repositories: List<RepositoryItem>
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
