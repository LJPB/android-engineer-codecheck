package jp.co.yumemi.android.code_check.ui.component.repository_search

import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.co.yumemi.android.code_check.data.structure.github.RepositoryItem
import jp.co.yumemi.android.code_check.data.structure.github.RepositoryOwner

/**
 * リポジトリーの検索結果をリスト([RepositoryList])で表示する際の各々のアイテム(リポジトリー)
 * @param repositoryItem 対象となるリポジトリー
 */
@Composable
fun RepositoryListItem(
    modifier: Modifier = Modifier,
    repositoryItem: RepositoryItem,
    itemOnClick: (RepositoryItem) -> Unit = {}
) {
    Box(modifier = modifier.clickable { itemOnClick(repositoryItem) }) {
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            fontSize = 12.sp,
            text = repositoryItem.fullName
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RepositoryListItemPreview() {
    RepositoryListItem(
        modifier = Modifier,
        repositoryItem = RepositoryItem(
            fullName = "fullName",
            owner = RepositoryOwner(avatarUrl = "https://example.com"),
            language = "language",
            stargazersCount = 100,
            watchersCount = 0,
            forksCount = 100,
            openIssuesCount = 0
        )
    )
}
