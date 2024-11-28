package jp.co.yumemi.android.code_check.ui.component.common

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * 読み込み状況
 */
enum class LoadingStatus {
    Success,
    Loading,
    Failed,
    Initial
}

/**
 * アイテムが最後まで表示されたら追加読み込み可能なLazyColumn
 * @param loadable 追加読み込み可能かどうか
 * @param loadingStatus 読み込み状況
 * @param loadContent 追加読み込みを実行する関数
 * @param success 読み込みに成功した時に表示するメッセージ
 * @param failed 読み込みに失敗した時に表示するメッセージ
 * @param loading 読み込み中に表示するメッセージ
 * @param content LazyColumnに表示するcontent
 */
@Composable
fun AddableLazyColumn(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    loadable: Boolean,
    loadingStatus: LoadingStatus,
    loadContent: () -> Unit,
    success: LazyListScope.() -> Unit = {},
    failed: LazyListScope.() -> Unit = {},
    loading: LazyListScope.() -> Unit = {},
    content: LazyListScope.() -> Unit
) {
    val isReachLastItem: Boolean by remember {
        derivedStateOf {
            val visibleLastItemIndex = state.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            val totalItemsCount = state.layoutInfo.totalItemsCount
            visibleLastItemIndex == totalItemsCount - 1
        }
    }

    LazyColumn(
        modifier = modifier,
        state = state,
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        flingBehavior = flingBehavior,
        userScrollEnabled = userScrollEnabled
    ) {
        content()
        when (loadingStatus) {
            LoadingStatus.Success -> success()
            LoadingStatus.Loading -> loading()
            LoadingStatus.Failed -> failed()
            else -> {}
        }
    }

    if (isReachLastItem && loadable && loadingStatus != LoadingStatus.Loading) {
        // 最後のコンテンツが現れて、読み込み可能で、現在読み込み中でなければ追加読み込みをする
        loadContent()
    }
}

@Preview(showBackground = true)
@Composable
private fun AddableLazyListPreview() {
    AddableLazyColumn(
        modifier = Modifier,
        loadable = true,
        loadingStatus = LoadingStatus.Loading,
        loadContent = {},
    ) {
        items((1..10).toList()) {
            Text(text = it.toString())
        }
    }
}
