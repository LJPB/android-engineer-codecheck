package jp.co.yumemi.android.code_check.ui.component.repository_detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.window.core.layout.WindowWidthSizeClass
import jp.co.yumemi.android.code_check.data.model.http.github.RepositoryDetail
import jp.co.yumemi.android.code_check.data.model.http.github.RepositoryOwner

/**
 * リポジトリの詳細画面([RepositoryDetailScreen])に実際に表示する内容
 */
@Composable
fun RepositoryDetailScreenContent(
    modifier: Modifier = Modifier,
    repositoryDetail: RepositoryDetail,
    windowWidthSizeClass: WindowWidthSizeClass
) {
    if (windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
        RepositoryDetailCompactContent(
            modifier = modifier,
            repositoryDetail = repositoryDetail
        )
    } else {
        RepositoryDetailContent(
            modifier = modifier,
            repositoryDetail = repositoryDetail
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RepositoryDetailScreenContentPreview() {
    RepositoryDetailScreenContent(
        modifier = Modifier,
        repositoryDetail = RepositoryDetail(
            fullName = "fullName",
            owner = RepositoryOwner(avatarUrl = "https://example.com"),
            language = "language",
            stargazersCount = 100,
            watchersCount = 0,
            forksCount = 100,
            openIssuesCount = 0
        ),
        windowWidthSizeClass = WindowWidthSizeClass.COMPACT
    )
}
