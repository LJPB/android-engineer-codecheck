package jp.co.yumemi.android.code_check.ui.component.repository_search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.model.http.github.RepositoryDetail
import jp.co.yumemi.android.code_check.data.model.http.github.RepositoryOwner

@Composable
fun SearchResultScreen(
    modifier: Modifier = Modifier,
    searchWord: String,
    repositoryDetailList: List<RepositoryDetail>,
    itemOnClick: (RepositoryDetail) -> Unit = {}
) {
    Column(modifier = modifier.fillMaxSize()) {
        // 検索ワードを表示
        Text(
            modifier = Modifier
                .padding(
                    horizontal = dimensionResource(R.dimen.listItemHorizontalPadding),
                    vertical = dimensionResource(R.dimen.listItemHorizontalPadding)
                )
                .fillMaxWidth()
                .wrapContentHeight(),
            style = MaterialTheme.typography.bodySmall,
            text = stringResource(R.string.searchResultOf, searchWord)
        )

        // 検索結果を表示
        if (repositoryDetailList.isEmpty()) {
            EmptyScreen(modifier = modifier.fillMaxSize())
        } else {
            RepositoryList(
                repositoryDetailList = repositoryDetailList,
                itemOnClick = itemOnClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchResultContentPreview() {
    val repositoryDetail = RepositoryDetail(
        fullName = "fullName",
        owner = RepositoryOwner(avatarUrl = "https://example.com"),
        language = "language",
        stargazersCount = 100,
        watchersCount = 0,
        forksCount = 100,
        openIssuesCount = 0
    )
    SearchResultScreen(
        modifier = Modifier,
        searchWord = "kotlin",
        repositoryDetailList = listOf(
            repositoryDetail,
            repositoryDetail,
            repositoryDetail,
            repositoryDetail
        ),
    ) {}
}


@Preview(showBackground = true)
@Composable
private fun SearchResultContentEmptyPreview() {
    SearchResultScreen(
        modifier = Modifier,
        searchWord = "kotlin",
        repositoryDetailList = listOf(),
    ) {}
}
