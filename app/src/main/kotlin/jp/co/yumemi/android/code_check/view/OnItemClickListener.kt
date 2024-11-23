package jp.co.yumemi.android.code_check.view

import jp.co.yumemi.android.code_check.data.structure.github.RepositoryInfo

interface OnItemClickListener {
    fun itemClick(item: RepositoryInfo)
}
