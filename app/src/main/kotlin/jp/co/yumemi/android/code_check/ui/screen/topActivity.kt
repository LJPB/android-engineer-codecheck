/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.screen

import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.data.repository.http.common.client.HttpRequestClient
import jp.co.yumemi.android.code_check.ui.screen.github.repository.GitHubRepositorySearchApp
import jp.co.yumemi.android.code_check.ui.theme.AppTheme
import javax.inject.Inject

@AndroidEntryPoint
class TopActivity : ComponentActivity() {

    // HttpRequestClientはViewModel内でのみ(間接的に)使用しているため
    // setUpやcloseなどの処理はViewModelのライフサイクルで行うと無駄がありませんでした
    // 具体的には(今の実装では)AppViewModelのinitブロックでsetUpを呼び、onClearedでcloseを呼ぶと良かったです
    // Activityでこれらの処理を行った場合、画面が回転しただけでもcloseと再取得が行われて無駄な処理が発生してしまいます
    // ただし、(Ktorの)HttpClient(Engine)の使い回しの安全性については学習が足りていないので、真に適切な呼び出しタイミングはわかっていません
    @Inject
    lateinit var httpClient: HttpRequestClient

    private val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val connectivityManager =
            ContextCompat.getSystemService(this, ConnectivityManager::class.java)

        // ネットの接続状況の初期化
        appViewModel.changeNetworkState(connectivityManager?.activeNetwork != null)

        // 現在のネットの接続状況に関するコールバック
        connectivityManager?.registerDefaultNetworkCallback(
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    appViewModel.changeNetworkState(true)
                }

                override fun onLost(network: Network) {
                    appViewModel.changeNetworkState(false)
                }
            }
        )

        httpClient.setUp()
        enableEdgeToEdge()
        setContent {
            AppTheme {
                GitHubRepositorySearchApp(viewModel = appViewModel)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        httpClient.close()
    }
}
