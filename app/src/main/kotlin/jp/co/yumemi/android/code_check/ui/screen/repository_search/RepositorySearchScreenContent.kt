package jp.co.yumemi.android.code_check.ui.screen.repository_search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.http.github.GitHubStatusCodes
import jp.co.yumemi.android.code_check.data.structure.github.RepositoryDetail
import jp.co.yumemi.android.code_check.data.structure.github.RepositorySearchResult
import jp.co.yumemi.android.code_check.ui.component.common.AppSearchBar
import jp.co.yumemi.android.code_check.ui.component.repository_search.RepositoryList

/**
 * 検索画面に表示する内容
 * @param query 検索バーに表示するテキスト
 * @param onQueryChange 検索バーの入力イベント
 * @param onQueryClear [query]のクリアイベント
 * @param onSearch 検索
 * @param repositoryOnClick 検索結果として表示されるリポジトリのクリックイベント
 * @param searchResult 検索結果
 */
@Composable
fun RepositorySearchScreenContent(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onQueryClear: () -> Unit,
    onSearch: (String) -> Unit,
    repositoryOnClick: (RepositoryDetail) -> Unit,
    searchResult: RepositorySearchResult
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            query = query,
            placeHolderText = stringResource(R.string.searchInputText_hint),
            onQueryChange = onQueryChange,
            onQueryClear = onQueryClear,
            onSearch = onSearch
        )
        when (searchResult.status) {
            GitHubStatusCodes.SUCCESS -> RepositoryList(
                repositoryDetailList = searchResult.repositories,
                itemOnClick = repositoryOnClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RepositorySearchScreenPreview() {
    RepositorySearchScreenContent(
        modifier = Modifier,
        query = "query",
        onQueryChange = {},
        onQueryClear = {},
        onSearch = {},
        repositoryOnClick = {},
        searchResult = RepositorySearchResult(listOf())
    )
}
