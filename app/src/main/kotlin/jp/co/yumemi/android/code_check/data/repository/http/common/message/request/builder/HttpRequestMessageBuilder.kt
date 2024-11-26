package jp.co.yumemi.android.code_check.data.repository.http.common.message.request.builder

import jp.co.yumemi.android.code_check.data.repository.http.common.message.request.HttpRequestMessage

/**
 * HTTPリクエストメッセージ([HttpRequestMessage])を作るビルダー
 */
interface HttpRequestMessageBuilder {
    fun build(): HttpRequestMessage

    fun clear()
}
