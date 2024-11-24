package jp.co.yumemi.android.code_check.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import jp.co.yumemi.android.code_check.data.structure.github.RepositoryItem
import jp.co.yumemi.android.code_check.data.structure.github.RepositoryOwner

/**
 * 検索結果として表示するリポジトリのリスト
 */
@Composable
fun RepositoryList(
    modifier: Modifier = Modifier,
    repositoryItemList: List<RepositoryItem>,
    itemOnClick: (RepositoryItem) -> Unit = {}
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        itemsIndexed(repositoryItemList) { index, item ->
            RepositoryListItem(
                repositoryItem = item,
                itemOnClick = itemOnClick
            )
            if (repositoryItemList.size - 1 > index) {
                HorizontalDivider()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RepositoryListPreview() {
    val repositoryItem = RepositoryItem(
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
        repositoryItemList = listOf(
            repositoryItem,
            repositoryItem,
            repositoryItem,
            repositoryItem,
        )
    )
}
