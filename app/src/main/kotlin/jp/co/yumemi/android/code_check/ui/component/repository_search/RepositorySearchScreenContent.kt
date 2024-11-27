package jp.co.yumemi.android.code_check.ui.component.repository_search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.model.http.github.RepositoryDetail
import jp.co.yumemi.android.code_check.data.model.http.github.RepositorySearchResponse
import jp.co.yumemi.android.code_check.data.repository.http.common.message.common.HttpStatus
import jp.co.yumemi.android.code_check.data.repository.http.common.message.response.HttpResponseMessage
import jp.co.yumemi.android.code_check.ui.component.common.AppSearchBar

/**
 * 検索画面に表示する内容
 * @param query 検索バーに表示するテキスト
 * @param onQueryChange 検索バーの入力イベント
 * @param onQueryClear [query]のクリアイベント
 * @param onSearch 検索
 * @param repositoryOnClick 検索結果として表示されるリポジトリのクリックイベント
 * @param searchResponse 検索結果
 */
@Composable
fun RepositorySearchScreenContent(
    modifier: Modifier = Modifier,
    networkIsActive: Boolean,
    query: String,
    onQueryChange: (String) -> Unit,
    onQueryClear: () -> Unit,
    onSearch: (String) -> Unit,
    repositoryOnClick: (RepositoryDetail) -> Unit,
    searchResponse: HttpResponseMessage<RepositorySearchResponse>
) {
    // リップルエフェクトを無効にするためのもの
    val interactionSource = remember { MutableInteractionSource() }

    // 親Columnにフォーカスを伝播させるためのもの
    val focusRequester = remember { FocusRequester() }

    val keyboardController = LocalSoftwareKeyboardController.current
    val hideKeyboard: () -> Unit = { keyboardController?.hide() }
    Column(
        modifier = modifier
            .fillMaxSize()
            .focusRequester(focusRequester)
            .focusTarget()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = hideKeyboard
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            query = query,
            placeHolderText = stringResource(R.string.searchInputText_hint),
            onQueryChange = onQueryChange,
            onQueryClear = onQueryClear,
            onSearch = {
                if (networkIsActive && it.isNotBlank()) { // ネットに接続していて、かつ入力文字が空白でない場合に検索できる
                    hideKeyboard()
                    onSearch(it)
                }
            }
        )
        when (searchResponse.status) {
            HttpStatus.SUCCESS -> RepositoryList(
                repositoryDetailList = searchResponse.body.responseBody.repositories,
                itemOnClick = repositoryOnClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RepositorySearchScreenPreview() {
    RepositorySearchScreenContent(
        modifier = Modifier,
        networkIsActive = true,
        query = "query",
        onQueryChange = {},
        onQueryClear = {},
        onSearch = {},
        repositoryOnClick = {},
        searchResponse = HttpResponseMessage(
            status = HttpStatus.INITIAL,
            statusMessage = "",
            headers = mapOf(),
            body = RepositorySearchResponse()
        )
    )
}
