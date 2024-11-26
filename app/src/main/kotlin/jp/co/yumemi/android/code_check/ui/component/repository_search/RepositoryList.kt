package jp.co.yumemi.android.code_check.ui.component.repository_search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import jp.co.yumemi.android.code_check.data.model.http.github.RepositoryDetail
import jp.co.yumemi.android.code_check.data.model.http.github.RepositoryOwner

/**
 * 検索結果として表示するリポジトリのリスト
 */
@Composable
fun RepositoryList(
    modifier: Modifier = Modifier,
    repositoryDetailList: List<RepositoryDetail>,
    itemOnClick: (RepositoryDetail) -> Unit = {}
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        itemsIndexed(repositoryDetailList) { index, item ->
            RepositoryListItem(
                repositoryDetail = item,
                itemOnClick = itemOnClick
            )
            if (repositoryDetailList.size - 1 > index) {
                HorizontalDivider()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RepositoryListPreview() {
    val repositoryDetail = RepositoryDetail(
        fullName = "fullName",
        owner = RepositoryOwner(avatarUrl = "https://example.com"),
        language = "language",
        stargazersCount = 100,
        watchersCount = 0,
        forksCount = 100,
        openIssuesCount = 0
    )
    RepositoryList(
        modifier = Modifier,
        repositoryDetailList = listOf(
            repositoryDetail,
            repositoryDetail,
            repositoryDetail,
            repositoryDetail,
        )
    )
}
