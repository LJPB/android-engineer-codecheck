package jp.co.yumemi.android.code_check.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import jp.co.yumemi.android.code_check.ui.component.common.NetworkErrorMessage
import jp.co.yumemi.android.code_check.ui.screen.repository_detail.RepositoryDetailDestination
import jp.co.yumemi.android.code_check.ui.screen.repository_detail.repositoryDetailScreen
import jp.co.yumemi.android.code_check.ui.screen.repository_search.RepositorySearchDestination
import jp.co.yumemi.android.code_check.ui.screen.repository_search.repositorySearchScreen

@Composable
fun GitHubRepositorySearchApp(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel
) {
    val navController = rememberNavController()

    val networkState = viewModel.networkState.collectAsStateWithLifecycle()
    val isNetworkActive by networkState

    Scaffold { paddingValue ->
        Column(modifier = modifier.padding(paddingValue)) {
            AnimatedVisibility(!isNetworkActive) {
                // contentはtrueで表示される
                // isNetworkActiveがfalseの時にエラーメッセージを表示したいから!networkIsActiveを渡している
                NetworkErrorMessage()
            }
            NavHost(
                navController = navController,
                startDestination = RepositorySearchDestination
            ) {
                // リポジトリーの検索画面 かつ ホーム画面
                repositorySearchScreen(networkState = networkState) {
                    navController.navigate(RepositoryDetailDestination(it))
                }

                // リポジトリーの詳細画面
                repositoryDetailScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GitHubRepositorySearchAppPreview() {
    GitHubRepositorySearchApp(
        modifier = Modifier,
        viewModel = viewModel()
    )
}
