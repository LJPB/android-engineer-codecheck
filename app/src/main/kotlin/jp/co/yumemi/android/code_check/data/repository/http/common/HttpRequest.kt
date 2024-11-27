package jp.co.yumemi.android.code_check.data.repository.http.common

import jp.co.yumemi.android.code_check.data.repository.http.common.executor.HttpRequestExecutor

/**
 * HTTPリクエストを実装するクラスに継承(実装)させる
 */
abstract class HttpRequest(protected open val httpRequestExecutor: HttpRequestExecutor)
