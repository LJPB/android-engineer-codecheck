package jp.co.yumemi.android.code_check.ui.screen.repository_search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import jp.co.yumemi.android.code_check.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.data.http.NetworkState
import jp.co.yumemi.android.code_check.data.structure.github.RepositoryDetail
import jp.co.yumemi.android.code_check.ui.RepositorySearchViewModel
import java.util.Date

@Composable
fun RepositorySearchScreen(
    modifier: Modifier = Modifier,
    viewModel: RepositorySearchViewModel,
    repositoryOnClick: (RepositoryDetail) -> Unit,
) {
    val context = LocalContext.current
    val result by viewModel.repositorySearchResult.collectAsState()
    val searchWord by viewModel.searchWord.collectAsState()
    RepositorySearchScreenContent(
        modifier = modifier,
        query = searchWord,
        onQueryChange = viewModel::changeSearchWord,
        onQueryClear = viewModel::clearSearchWord,
        onSearch = {
            if (NetworkState.isActiveNetwork(context) && it.isNotBlank()) {
                lastSearchDate = Date()
                viewModel.searchWithWord(it)
            }
        },
        repositoryOnClick = repositoryOnClick,
        searchResult = result
    )
}
