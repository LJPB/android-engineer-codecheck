package jp.co.yumemi.android.code_check.ui.screen

import android.net.ConnectivityManager
import android.net.Network
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.getSystemService
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
) {
    val navController = rememberNavController()

    val context = LocalContext.current
    val connectivityManager = getSystemService(context, ConnectivityManager::class.java)
    // ネットの接続状況
    var networkIsActive by remember { mutableStateOf(connectivityManager?.activeNetwork != null) }
    // 現在のネットの接続状況に関するコールバック
    connectivityManager?.registerDefaultNetworkCallback(
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                networkIsActive = true
            }

            override fun onLost(network: Network) {
                networkIsActive = false
            }
        }
    )

    Scaffold { paddingValue ->
        Column(modifier = modifier.padding(paddingValue)) {
            AnimatedVisibility(!networkIsActive) {
                // contentはtrueで表示される
                // networkIsActiveがfalseの時にエラーメッセージを表示したいから!networkIsActiveを渡している
                NetworkErrorMessage()
            }
            NavHost(
                navController = navController,
                startDestination = RepositorySearchDestination
            ) {
                // リポジトリーの検索画面 かつ ホーム画面
                repositorySearchScreen(networkIsActive = networkIsActive) {
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
    )
}
