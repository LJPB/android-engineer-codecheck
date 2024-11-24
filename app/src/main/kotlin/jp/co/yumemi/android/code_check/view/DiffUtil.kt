package jp.co.yumemi.android.code_check.view

import androidx.recyclerview.widget.DiffUtil
import jp.co.yumemi.android.code_check.data.structure.github.RepositoryInfo
import jp.co.yumemi.android.code_check.data.structure.github.RepositoryItem

val diff_util = object : DiffUtil.ItemCallback<RepositoryItem>() {
    override fun areItemsTheSame(oldItem: RepositoryItem, newItem: RepositoryItem): Boolean {
        return oldItem.fullName == newItem.fullName
    }

    override fun areContentsTheSame(oldItem: RepositoryItem, newItem: RepositoryItem): Boolean {
        return oldItem == newItem
    }
}
