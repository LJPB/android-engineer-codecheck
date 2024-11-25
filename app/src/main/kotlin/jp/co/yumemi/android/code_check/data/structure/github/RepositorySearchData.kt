package jp.co.yumemi.android.code_check.data.structure.github

import android.os.Parcelable
import io.ktor.http.HttpStatusCode
import kotlinx.parcelize.Parcelize
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
    val repositories: List<RepositoryDetail>,

    @Transient
    var status: Set<HttpStatusCode> = emptySet()
)

/**
 * 個々のリポジトリを表すデータクラス
 */
@Parcelize
@Serializable
data class RepositoryDetail(
    @SerialName("full_name")
    val fullName: String,

    @SerialName("owner")
    val owner: RepositoryOwner,

    @SerialName("language")
    val language: String?,

    @SerialName("stargazers_count")
    val stargazersCount: Long,

    @SerialName("watchers_count")
    val watchersCount: Long,

    @SerialName("forks_count")
    val forksCount: Long,

    @SerialName("open_issues_count")
    val openIssuesCount: Long,
) : Parcelable

/**
 * リポジトリのownerを表すデータクラス
 */
@Parcelize
@Serializable
data class RepositoryOwner(
    @SerialName("avatar_url")
    val avatarUrl: String
) : Parcelable
