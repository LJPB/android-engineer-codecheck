package jp.co.yumemi.android.code_check.ui.screen.repository_search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import jp.co.yumemi.android.code_check.data.model.http.github.RepositoryDetail
import jp.co.yumemi.android.code_check.ui.component.repository_search.RepositorySearchScreenContent
import kotlinx.serialization.Serializable

@Composable
fun RepositorySearchScreen(
    modifier: Modifier = Modifier,
    networkIsActive: MutableState<Boolean>,
    viewModel: RepositorySearchViewModel,
    repositoryOnClick: (RepositoryDetail) -> Unit,
) {
    val searchResponse by viewModel.repositorySearchResponse.collectAsState()
    val searchWord by viewModel.searchWord.collectAsState()
    RepositorySearchScreenContent(
        modifier = modifier,
        networkIsActive = networkIsActive,
        query = searchWord,
        onQueryChange = viewModel::changeSearchWord,
        onQueryClear = viewModel::clearSearchWord,
        onSearch = viewModel::searchWithWord,
        repositoryOnClick = repositoryOnClick,
        searchResponse = searchResponse
    )
}

@Serializable
object RepositorySearchDestination

fun NavGraphBuilder.repositorySearchScreen(
    networkIsActive: MutableState<Boolean>,
    repositoryOnClick: (RepositoryDetail) -> Unit
) {
    composable<RepositorySearchDestination> {
        RepositorySearchScreen(
            viewModel = hiltViewModel(),
            networkIsActive = networkIsActive,
            repositoryOnClick = repositoryOnClick
        )
    }
}
