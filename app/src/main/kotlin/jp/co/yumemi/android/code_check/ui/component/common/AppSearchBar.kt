package jp.co.yumemi.android.code_check.ui.component.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import jp.co.yumemi.android.code_check.R

/**
 * 検索バー
 * @param query 表示するクエリ
 * @param placeHolderText クエリが入力されていない時に表示するテキスト
 * @param onQueryChange 文字を入力された時に実行するアクション
 * @param onQueryClear クエリをクリアする時に実行するアクション
 * @param onSearch 検索アクション
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    placeHolderText: String,
    onQueryChange: (String) -> Unit,
    onQueryClear: () -> Unit,
    onSearch: (String) -> Unit
) {
    SearchBar(
        modifier = modifier,
        inputField = {
            SearchInputField(
                query = query,
                placeHolderText = placeHolderText,
                onQueryChange = onQueryChange,
                onQueryClear = onQueryClear,
                onSearch = onSearch
            )
        },
        // 展開しない
        expanded = false,
        onExpandedChange = {},
        // 展開しないから中身もない
        content = {}
    )
}

/**
 * 検索バーで実際に入力するフィールド
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchInputField(
    modifier: Modifier = Modifier,
    query: String,
    placeHolderText: String,
    onQueryChange: (String) -> Unit,
    onQueryClear: () -> Unit,
    onSearch: (String) -> Unit
) {
    SearchBarDefaults.InputField(
        modifier = modifier,
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        placeholder = {
            Text(
                text = placeHolderText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        trailingIcon = {
            // クエリが入力されていたらクリアボタンを表示する
            if (query.isNotBlank()) {
                IconButton(onClick = onQueryClear) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null
                    )
                }
            }
        },
        // 展開しない
        expanded = false,
        onExpandedChange = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun AppSearchBarPreview() {
    AppSearchBar(
        modifier = Modifier,
        query = "",
        placeHolderText = stringResource(R.string.searchInputText_hint),
        onQueryChange = {},
        onQueryClear = {},
        onSearch = {}
    )
}
