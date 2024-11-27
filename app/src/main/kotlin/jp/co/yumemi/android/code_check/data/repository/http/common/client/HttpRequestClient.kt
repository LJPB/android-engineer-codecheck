package jp.co.yumemi.android.code_check.data.repository.http.common.client

import jp.co.yumemi.android.code_check.data.repository.http.common.executor.HttpRequestExecutor

/**
 * HTTP通信のリクエストを行う
 */
interface HttpRequestClient : HttpRequestExecutor {
    /**
     * HTTPリクエストを行うためのセットアップ
     */
    fun setUp()

    /**
     * リソースの解放
     */
    fun close()
}
