package jp.co.yumemi.android.code_check.ui.component.repository_search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.ui.component.common.CenterContainer

/**
 * 検索結果がなかった時に表示する画面
 */
@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
) {
    CenterContainer(modifier = modifier.fillMaxSize()) {
        Text(
            style = MaterialTheme.typography.bodyLarge,
            text = stringResource(R.string.searchResultEmpty)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyScreenPreview() {
    EmptyScreen(
        modifier = Modifier,
    )
}
