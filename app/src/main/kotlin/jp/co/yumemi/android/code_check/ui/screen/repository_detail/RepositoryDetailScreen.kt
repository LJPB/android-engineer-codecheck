package jp.co.yumemi.android.code_check.ui.screen.repository_detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.window.core.layout.WindowWidthSizeClass

@Composable
fun RepositoryDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: RepositoryDetailViewModel,
    windowWidthSizeClass: WindowWidthSizeClass
) {
    RepositoryDetailScreenContent(
        modifier = modifier,
        repositoryDetail = viewModel.repositoryDetail,
        windowWidthSizeClass = windowWidthSizeClass
    )
}
