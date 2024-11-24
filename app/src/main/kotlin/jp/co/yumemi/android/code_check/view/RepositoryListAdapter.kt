package jp.co.yumemi.android.code_check.view

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import jp.co.yumemi.android.code_check.data.structure.github.RepositoryItem

class RepositoryListAdapter(
    private val onItemClickListener: OnItemClickListener,
) : ListAdapter<RepositoryItem, RepositoryListViewHolder>(diff_util) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryListViewHolder {
        val view = RepositoryListItemView(parent.context)
        return RepositoryListViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: RepositoryListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
