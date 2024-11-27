package jp.co.yumemi.android.code_check.data.repository.http.common.message.common

object HttpStatus {
    /**
     * 初期値
     */
    const val INITIAL = -3

    /**
     * 不明なエラー
     */
    const val UNKNOWN = -2

    /**
     * タイムアウト
     */
    const val TIMEOUT = -1

    /**
     * 読み込み中
     */
    const val LOADING = 0

    const val SUCCESS = 200
}
