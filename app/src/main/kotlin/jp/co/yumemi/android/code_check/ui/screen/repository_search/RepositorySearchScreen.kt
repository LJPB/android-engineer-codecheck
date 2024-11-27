package jp.co.yumemi.android.code_check.ui.screen.repository_search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import jp.co.yumemi.android.code_check.data.model.http.github.RepositoryDetail
import jp.co.yumemi.android.code_check.data.util.NetworkState
import jp.co.yumemi.android.code_check.ui.component.repository_search.RepositorySearchScreenContent
import kotlinx.serialization.Serializable

@Composable
fun RepositorySearchScreen(
    modifier: Modifier = Modifier,
    viewModel: RepositorySearchViewModel,
    repositoryOnClick: (RepositoryDetail) -> Unit,
) {
    val context = LocalContext.current
    val searchResponse by viewModel.repositorySearchResponse.collectAsState()
    val searchWord by viewModel.searchWord.collectAsState()
    RepositorySearchScreenContent(
        modifier = modifier,
        query = searchWord,
        onQueryChange = viewModel::changeSearchWord,
        onQueryClear = viewModel::clearSearchWord,
        onSearch = {
            if (NetworkState.isActiveNetwork(context) && it.isNotBlank()) {
                viewModel.searchWithWord(it)
            }
        },
        repositoryOnClick = repositoryOnClick,
        searchResponse = searchResponse
    )
}

@Serializable
object RepositorySearchDestination

fun NavGraphBuilder.repositorySearchScreen(repositoryOnClick: (RepositoryDetail) -> Unit) {
    composable<RepositorySearchDestination> {
        RepositorySearchScreen(
            viewModel = hiltViewModel(),
            repositoryOnClick = repositoryOnClick
        )
    }
}
