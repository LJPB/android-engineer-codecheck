package jp.co.yumemi.android.code_check.data.model.http.github

/**
 * リポジトリーの検索結果を格納
 * @param rateLimitData レート制限に関する情報
 * @param hasNextPage 次のページが存在するかどうか
 * @param responseBody 検索結果
 */
data class RepositorySearchResponse(
    val rateLimitData: RateLimitData = RateLimitData(),
    val hasNextPage: Boolean = false,
    val responseBody: RepositorySearchResult = RepositorySearchResult()
)
