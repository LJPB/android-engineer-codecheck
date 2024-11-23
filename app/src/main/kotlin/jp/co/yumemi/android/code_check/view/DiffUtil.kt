package jp.co.yumemi.android.code_check.view

import androidx.recyclerview.widget.DiffUtil
import jp.co.yumemi.android.code_check.data.structure.github.RepositoryInfo

val diff_util = object : DiffUtil.ItemCallback<RepositoryInfo>() {
    override fun areItemsTheSame(oldItem: RepositoryInfo, newItem: RepositoryInfo): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: RepositoryInfo, newItem: RepositoryInfo): Boolean {
        return oldItem == newItem
    }
}
