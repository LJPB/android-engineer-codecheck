package jp.co.yumemi.android.code_check.view

import jp.co.yumemi.android.code_check.data.structure.github.RepositoryItem

interface OnItemClickListener {
    fun itemClick(item: RepositoryItem)
}
