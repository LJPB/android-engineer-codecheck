package jp.co.yumemi.android.code_check.ui.screen.github.repository.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import jp.co.yumemi.android.code_check.data.model.http.github.RepositoryDetail
import jp.co.yumemi.android.code_check.ui.component.github.repository.search.RepositorySearchScreenContent
import kotlinx.serialization.Serializable

@Composable
fun RepositorySearchScreen(
    modifier: Modifier = Modifier,
    networkState: State<Boolean>,
    viewModel: RepositorySearchViewModel,
    repositoryOnClick: (RepositoryDetail) -> Unit,
) {
    val searchResponse by viewModel.repositorySearchResponse.collectAsState()
    val searchWord by viewModel.searchWord.collectAsState()
    val loadingStatus by viewModel.loadingStatus.collectAsState()
    val currentSort by viewModel.currentSort.collectAsState()
    val currentOrder by viewModel.currentOrder.collectAsState()
    val isNetworkActive by networkState
    RepositorySearchScreenContent(
        modifier = modifier,
        isNetworkActive = isNetworkActive,
        loadingStatus = loadingStatus,
        loadContent = viewModel::loadNextPage,
        query = searchWord,
        onQueryChange = viewModel::changeSearchWord,
        onQueryClear = viewModel::clearSearchWord,
        onSearch = viewModel::searchWithWord,
        currentSort = currentSort,
        currentOrder = currentOrder,
        sortOnClick = viewModel::setSort,
        orderOnClick = viewModel::setOrder,
        clearOrder = viewModel::clearOrder,
        clearSort = viewModel::clearSort,
        repositoryOnClick = repositoryOnClick,
        responseMessage = searchResponse
    )
}

@Serializable
object RepositorySearchDestination

fun NavGraphBuilder.repositorySearchScreen(
    networkState: State<Boolean>,
    repositoryOnClick: (RepositoryDetail) -> Unit
) {
    composable<RepositorySearchDestination> {
        RepositorySearchScreen(
            viewModel = hiltViewModel(),
            networkState = networkState,
            repositoryOnClick = repositoryOnClick
        )
    }
}
