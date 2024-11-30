package jp.co.yumemi.android.code_check.data.model.http.github

import jp.co.yumemi.android.code_check.data.repository.http.github.request.repository.RepositorySearchQueryType

// こちらは「リポジトリー」と長音記号がついています
/**
 * リポジトリーの検索結果を格納
 * @param searchWord 検索した単語
 * @param sort ソートの条件
 * @param order 並び順の条件
 * @param rateLimitData レート制限に関する情報
 * @param hasNextPage 次のページが存在するかどうか
 * @param responseBody 検索結果
 */
data class RepositorySearchResponse(
    val searchWord: String = "",
    val sort: RepositorySearchQueryType.Sort? = null,
    val order: RepositorySearchQueryType.Order? = null,
    val rateLimitData: RateLimitData = RateLimitData(),
    val hasNextPage: Boolean = false,
    val responseBody: RepositorySearchResult = RepositorySearchResult(),
)
