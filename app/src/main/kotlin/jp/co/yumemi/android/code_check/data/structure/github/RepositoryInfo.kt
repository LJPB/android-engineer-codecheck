package jp.co.yumemi.android.code_check.data.structure.github

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 検索結果として取得される各々のリポジトリの情報
 */
@Parcelize
data class RepositoryInfo(
    val name: String,
    val ownerIconUrl: String,
    val language: String,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
) : Parcelable
