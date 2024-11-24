package jp.co.yumemi.android.code_check.view

import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.data.structure.github.RepositoryItem

class RepositoryListViewHolder(
    private val repositoryListItemView: RepositoryListItemView,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.ViewHolder(repositoryListItemView) {

    /**
     * [repositoryItem]のデータをviewに紐付ける
     */
    fun bind(repositoryItem: RepositoryItem) {
        repositoryListItemView.repositoryItem = repositoryItem
        repositoryListItemView.setOnClickListener {
            onItemClickListener.itemClick(repositoryItem)
        }
    }
}
