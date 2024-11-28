package jp.co.yumemi.android.code_check.ui.component.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 * これで囲んだComposeをクリックするとメニューが表示するようになる
 * メニューの開閉はこのComposableが勝手にする
 *
 * @param itemTextList メニューアイテムに表示するテキストのリスト
 * @param itemOnClick アイテムのクリックイベント 対応するインデックスが渡される
 * @param content クリックするとメニューを表示させたいCompose
 */
@Composable
fun WithMenu(
    modifier: Modifier = Modifier,
    itemTextList: List<String>,
    itemOnClick: (Int) -> Unit,
    content: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Box {
        Box(
            modifier = modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { expanded = true }
                )
        ) {
            content()
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            itemTextList.forEachIndexed { index, itemText ->
                DropdownMenuItem(
                    text = {
                        Text(text = itemText)
                    },
                    onClick = {
                        itemOnClick(index)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun WithMenuPreview() {
    WithMenu(
        itemTextList = listOf("item", "item", "item"),
        itemOnClick = {}
    ) {
        Text(text = "text")
    }
}
