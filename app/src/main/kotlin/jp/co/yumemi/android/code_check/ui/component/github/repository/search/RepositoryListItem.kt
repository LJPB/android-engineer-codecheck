package jp.co.yumemi.android.code_check.ui.component.github.repository.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.model.http.github.RepositoryDetail
import jp.co.yumemi.android.code_check.data.model.http.github.RepositoryOwner

/**
 * リポジトリーの検索結果をリスト([RepositoryList])で表示する際の各々のアイテム(リポジトリー)
 * @param repositoryDetail 対象となるリポジトリー
 */
@Composable
fun RepositoryListItem(
    modifier: Modifier = Modifier,
    repositoryDetail: RepositoryDetail,
    itemOnClick: (RepositoryDetail) -> Unit = {}
) {
    Box(modifier = modifier.clickable { itemOnClick(repositoryDetail) }) {
        Text(
            modifier = Modifier
                .padding(
                    horizontal = dimensionResource(R.dimen.listItemHorizontalPadding),
                    vertical = dimensionResource(R.dimen.listItemHorizontalPadding)
                )
                .fillMaxWidth()
                .wrapContentHeight(),
            fontSize = 12.sp,
            text = repositoryDetail.fullName
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RepositoryListItemPreview() {
    RepositoryListItem(
        modifier = Modifier,
        repositoryDetail = RepositoryDetail(
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
