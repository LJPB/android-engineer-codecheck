package jp.co.yumemi.android.code_check.ui.component.github.repository.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.model.http.github.RepositoryDetail
import jp.co.yumemi.android.code_check.data.model.http.github.RepositoryOwner

/**
 * WindowWidthSizeClassがCOMPACTな画面でリポジトリの詳細を表示する
 */
@Composable
fun RepositoryDetailCompactContent(
    modifier: Modifier = Modifier,
    repositoryDetail: RepositoryDetail
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = modifier
                .padding(16.dp)
                .widthIn(max = 240.dp)
                .aspectRatio(1f),
            model = repositoryDetail.owner.avatarUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Text( // リポジトリのフルネーム
            modifier = Modifier.padding(top = 16.dp),
            text = repositoryDetail.fullName,
            fontSize = 18.sp
        )
        RepositoryInfoContent(
            repositoryDetail = repositoryDetail,
            isCompactScreen = true
        )
    }
}

/**
 * WindowWidthSizeがCompact以外の画面でリポジトリの詳細を表示する
 */
@Composable
fun RepositoryDetailContent(
    modifier: Modifier = Modifier,
    repositoryDetail: RepositoryDetail
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = modifier
                .padding(16.dp)
                .widthIn(max = 240.dp)
                .aspectRatio(1f),
            model = repositoryDetail.owner.avatarUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Column {
            Text( // リポジトリのフルネーム
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = repositoryDetail.fullName,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(32.dp))
            RepositoryInfoContent(
                repositoryDetail = repositoryDetail,
                isCompactScreen = false
            )
        }
    }
}

/**
 * フォーク数などのリポジトリーに関する情報を表示する
 */
@Composable
private fun RepositoryInfoContent(
    modifier: Modifier = Modifier,
    repositoryDetail: RepositoryDetail,
    isCompactScreen: Boolean
) {
    val language = repositoryDetail.language
    val infoTextList = listOf(
        stringResource(R.string.stargazersCount, repositoryDetail.stargazersCount),
        stringResource(R.string.watchersCount, repositoryDetail.watchersCount),
        stringResource(R.string.forksCount, repositoryDetail.forksCount),
        stringResource(R.string.openIssuesCount, repositoryDetail.openIssuesCount),
    )
    Row(modifier = modifier) {
        Box(modifier = Modifier.weight(1f)) {
            if (!language.isNullOrBlank()) { // リポジトリで使われている言語情報があれば表示する
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp),
                    text = stringResource(R.string.written_language, language),
                    fontSize = 14.sp,
                    textAlign = if (isCompactScreen) TextAlign.Start else TextAlign.Center
                )
            }
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = if (isCompactScreen) Alignment.End else Alignment.Start
        ) {
            infoTextList.forEach {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp),
                    text = it,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RepositoryInfoContentPreview() {
    RepositoryInfoContent(
        modifier = Modifier,
        repositoryDetail = RepositoryDetail(
            fullName = "fullName",
            owner = RepositoryOwner(avatarUrl = "https://example.com"),
            language = "language",
            stargazersCount = 100,
            watchersCount = 0,
            forksCount = 100,
            openIssuesCount = 0,
        ),
        isCompactScreen = true
    )
}

@Preview(showBackground = true)
@Composable
private fun RepositoryDetailCompactContentPreview() {
    RepositoryDetailCompactContent(
        modifier = Modifier,
        repositoryDetail = RepositoryDetail(
            fullName = "fullName",
            owner = RepositoryOwner(avatarUrl = "https://example.com"),
            language = "language",
            stargazersCount = 100,
            watchersCount = 0,
            forksCount = 100,
            openIssuesCount = 0
        )
    )
}

@Preview(
    showBackground = true,
    widthDp = 840
)
@Composable
private fun RepositoryDetailContentPreview() {
    RepositoryDetailContent(
        modifier = Modifier,
        repositoryDetail = RepositoryDetail(
            fullName = "fullName",
            owner = RepositoryOwner(avatarUrl = "https://example.com"),
            language = "language",
            stargazersCount = 100,
            watchersCount = 0,
            forksCount = 100,
            openIssuesCount = 0
        )
    )
}
