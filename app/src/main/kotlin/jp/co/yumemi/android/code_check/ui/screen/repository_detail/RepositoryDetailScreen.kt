package jp.co.yumemi.android.code_check.ui.screen.repository_detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RepositoryDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: RepositoryDetailViewModel
) {
    RepositoryDetailScreenContent(
        modifier = modifier,
        repositoryDetail = viewModel.repositoryDetail
    )
}
