package jp.co.yumemi.android.code_check.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import jp.co.yumemi.android.code_check.ui.screen.repository_detail.RepositoryDetailDestination
import jp.co.yumemi.android.code_check.ui.screen.repository_detail.repositoryDetailScreen
import jp.co.yumemi.android.code_check.ui.screen.repository_search.RepositorySearchDestination
import jp.co.yumemi.android.code_check.ui.screen.repository_search.repositorySearchScreen

@Composable
fun GitHubRepositorySearchApp(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    Scaffold { paddingValue ->
        NavHost(
            modifier = modifier.padding(paddingValue),
            navController = navController,
            startDestination = RepositorySearchDestination
        ) {
            // リポジトリーの検索画面 かつ ホーム画面
            repositorySearchScreen {
                navController.navigate(RepositoryDetailDestination(it))
            }

            // リポジトリーの詳細画面
            repositoryDetailScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GitHubRepositorySearchAppPreview() {
    GitHubRepositorySearchApp(
        modifier = Modifier,
    )
}
