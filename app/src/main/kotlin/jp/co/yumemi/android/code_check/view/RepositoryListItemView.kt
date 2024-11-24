package jp.co.yumemi.android.code_check.view

import android.content.Context
import android.util.AttributeSet
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.AbstractComposeView
import jp.co.yumemi.android.code_check.data.structure.github.RepositoryItem
import jp.co.yumemi.android.code_check.ui.RepositoryListItem

// ComposeをViewとして表示するためのもの
class RepositoryListItemView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AbstractComposeView(context = context, attrs = attrs, defStyleAttr = defStyle) {

    var repositoryItem by mutableStateOf<RepositoryItem?>(null)

    @Composable
    override fun Content() {
        MaterialTheme {
            val item = repositoryItem
            if (item != null) {
                RepositoryListItem(repositoryItem = item)
            }
        }
    }
}
