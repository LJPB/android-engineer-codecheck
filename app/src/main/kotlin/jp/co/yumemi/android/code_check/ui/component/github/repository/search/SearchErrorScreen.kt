package jp.co.yumemi.android.code_check.ui.component.github.repository.search

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.co.yumemi.android.code_check.ui.component.common.CenterColumn
import jp.co.yumemi.android.code_check.ui.component.common.CenterContainer

/**
 * 検索エラーの時に表示する画面
 */
@Composable
fun SearchErrorScreen(
    modifier: Modifier = Modifier,
    statusCode: Int? = null,
    message: String
) {
    val textStyle = MaterialTheme.typography.bodyLarge
    val textColor = MaterialTheme.colorScheme.onSurface
    CenterContainer(modifier = modifier.fillMaxSize()) {
        CenterColumn {
            if (statusCode != null) {
                Text(
                    style = textStyle,
                    color = textColor,
                    text = statusCode.toString()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Text(
                style = textStyle,
                color = textColor,
                text = message
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchErrorScreenPreview() {
    SearchErrorScreen(
        modifier = Modifier,
        message = "message"
    )
}


@Preview(showBackground = true)
@Composable
private fun SearchErrorScreenWithStatusCodePreview() {
    SearchErrorScreen(
        modifier = Modifier,
        statusCode = 404,
        message = "Not Found"
    )
}
