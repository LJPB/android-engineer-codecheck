/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme

class TopActivity : ComponentActivity() {

    // TODO: RepositorySearchScreenで検索したDate()をRepositoryDetailScreenでLog.d("検索した日時", ...)の形式でLogcatに表示 

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                GitHubRepositorySearchApp()
            }
        }
    }
}
