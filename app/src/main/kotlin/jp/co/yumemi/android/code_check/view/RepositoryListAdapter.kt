package jp.co.yumemi.android.code_check.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.RepositoryInfo

class RepositoryListAdapter(
    private val onItemClickListener: OnItemClickListener,
) : ListAdapter<RepositoryInfo, RepositoryListViewHolder>(diff_util) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item, parent, false)
        return RepositoryListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryListViewHolder, position: Int) {
        val repositoryInfo = getItem(position)
        (holder.itemView.findViewById<View>(R.id.repositoryNameView) as TextView).text =
            repositoryInfo.name

        holder.itemView.setOnClickListener {
            onItemClickListener.itemClick(repositoryInfo)
        }
    }
}