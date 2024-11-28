package jp.co.yumemi.android.code_check.ui.component.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 * [content]を中央に表示するColumn
 */
@Composable
fun CenterColumn(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun CenterColumnPreview() {
    CenterColumn(
        modifier = Modifier,
    ) {
        Text(text = "aaa")
        Text(text = "bbbbbbbbb")
        Text(text = "c")
    }
}
