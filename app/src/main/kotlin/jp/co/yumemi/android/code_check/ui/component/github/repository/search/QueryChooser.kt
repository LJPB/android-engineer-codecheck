package jp.co.yumemi.android.code_check.ui.component.github.repository.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.repository.http.github.request.repository.RepositorySearchQueryType
import jp.co.yumemi.android.code_check.ui.component.common.CenterColumn
import jp.co.yumemi.android.code_check.ui.component.common.WithMenu

/**
 * List<Pair<>>の各要素から、渡された[key]を.firstとして持つ要素の.secondを取得する
 */
private fun <T, V> List<Pair<T, V>>.getValue(key: T?): V? =
    this.filter { it.first == key }.map { it.second }.firstOrNull()

/**
 * 検索条件を指定するためのCompose
 * @param currentSort 現在指定しているソートの条件
 * @param currentOrder 現在指定している並び順の条件
 * @param sortOnClick ソートの条件を選んだ時のアクション
 * @param orderOnClick 並び順の条件を選んだ時のアクション
 * @param clearSort ソートの条件のクリアイベント
 * @param clearOrder 並び順の条件のクリアイベント
 */
@Composable
fun QueryChooser(
    modifier: Modifier = Modifier,
    currentSort: RepositorySearchQueryType.Sort?,
    currentOrder: RepositorySearchQueryType.Order?,
    sortOnClick: (RepositorySearchQueryType.Sort) -> Unit,
    orderOnClick: (RepositorySearchQueryType.Order) -> Unit,
    clearSort: () -> Unit,
    clearOrder: () -> Unit,
) {
    // ソートに関するクエリとそのテキストの一覧
    val sortList = listOf(
        RepositorySearchQueryType.Sort.Stars to stringResource(R.string.sortStar),
        RepositorySearchQueryType.Sort.Forks to stringResource(R.string.sortFork),
        RepositorySearchQueryType.Sort.HelpWantedIssues to stringResource(R.string.sortIssues),
        RepositorySearchQueryType.Sort.Updated to stringResource(R.string.sortUpdated),
    )
    // 並び順に関するクエリとそのテキストの一覧
    val orderList = listOf(
        RepositorySearchQueryType.Order.Desc to stringResource(R.string.orderByDesc),
        RepositorySearchQueryType.Order.Asc to stringResource(R.string.orderByAsc),
    )
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 16.dp, alignment = Alignment.Start)
    ) {
        // 検索条件を指定するcontentであることを表すタイトル
        QueryText(
            text = stringResource(R.string.searchQueryChoose)
        )

        // ソートに関するクエリのリスト
        QueryList(
            currentItem = sortList.getValue(currentSort) ?: "",
            queryTitle = stringResource(R.string.sort),
            queryItem = sortList.map { it.second },
            itemOnClick = {
                val sort = sortList[it].first
                if (sort == currentSort) {
                    clearSort()
                } else {
                    sortOnClick(sort)
                }
            },
        )

        // 並び順に関するクエリのリスト
        QueryList(
            currentItem = orderList.getValue(currentOrder) ?: "",
            queryTitle = stringResource(R.string.order),
            queryItem = orderList.map { it.second },
            itemOnClick = {
                val order = orderList[it].first
                if (order == currentOrder) {
                    clearOrder()
                } else {
                    orderOnClick(order)
                }
            },
        )
    }
}

// このファイルで共通して使うクエリを表すText
@Composable
private fun QueryText(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface,
        text = text
    )
}

/**
 * 選択可能なクエリの一覧を表示する
 */
@Composable
private fun QueryList(
    modifier: Modifier = Modifier,
    currentItem: String = "",
    queryTitle: String,
    queryItem: List<String>,
    itemOnClick: (Int) -> Unit,
) {
    WithMenu(
        modifier = modifier,
        itemTextList = queryItem,
        itemOnClick = itemOnClick
    ) {
        CenterColumn {
            QueryText(text = queryTitle)
            AnimatedVisibility(currentItem != "") {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    modifier = modifier,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    text = currentItem
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun QueryTextPreview() {
    QueryText(
        modifier = Modifier,
        text = "query"
    )
}

@Preview(showBackground = true)
@Composable
private fun QueryListPreview() {
    QueryList(
        modifier = Modifier,
        queryTitle = "query",
        queryItem = listOf("item", "item", "item"),
    ) {}
}


@Preview(showBackground = true)
@Composable
private fun SetQueryPreview() {
    QueryChooser(
        modifier = Modifier,
        currentSort = null,
        currentOrder = null,
        sortOnClick = {},
        orderOnClick = {},
        clearSort = {},
        clearOrder = {},
    )
}
