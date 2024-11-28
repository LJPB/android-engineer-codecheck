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
