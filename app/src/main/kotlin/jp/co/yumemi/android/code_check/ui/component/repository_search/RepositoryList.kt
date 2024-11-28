package jp.co.yumemi.android.code_check.ui.component.repository_search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.model.http.github.RepositoryDetail
import jp.co.yumemi.android.code_check.data.model.http.github.RepositoryOwner
import jp.co.yumemi.android.code_check.data.model.http.github.RepositorySearchResponse
import jp.co.yumemi.android.code_check.ui.component.common.AddableLazyColumn
import jp.co.yumemi.android.code_check.ui.component.common.LoadingStatus

/**
 * 検索結果として表示するリポジトリのリスト
 * @param repositorySearchResponse 検索結果
 * @param loadingStatus 次のページの読み込み状況
 * @param loadContent 次のページの読み込みアクション
 * @param itemOnClick リストのアイテムのクリックアクション
 */
@Composable
fun RepositoryList(
    modifier: Modifier = Modifier,
    repositorySearchResponse: RepositorySearchResponse,
    loadingStatus: LoadingStatus,
    loadContent: () -> Unit,
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
                .fillMaxWidth(),
            style = MaterialTheme.typography.bodySmall,
            text = stringResource(R.string.searchResultOf, repositorySearchResponse.searchWord)
        )
        // 検索結果を表示
        AddableLazyColumn(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            loadable = repositorySearchResponse.hasNextPage,
            loadingStatus = loadingStatus,
            loadContent = loadContent,
            loading = {
                item {
                    CircularProgressIndicator(modifier = Modifier.padding(8.dp))
                }
            }
        ) {
            itemsIndexed(repositorySearchResponse.responseBody.repositories) { index, item ->
                RepositoryListItem(
                    repositoryDetail = item,
                    itemOnClick = itemOnClick
                )
                if (repositorySearchResponse.responseBody.repositories.size - 1 > index) {
                    HorizontalDivider()
                }
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
        loadingStatus = LoadingStatus.Initial,
        loadContent = {},
        repositorySearchResponse = RepositorySearchResponse()
    )
}
