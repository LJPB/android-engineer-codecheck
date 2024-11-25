package jp.co.yumemi.android.code_check.data.http.github.exception

import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.HttpResponse
import jp.co.yumemi.android.code_check.data.structure.github.RateLimitData

/**
 * GitHubのレート制限に引っかかった時の例外
 * @param response レート制限時のレスポンス
 */
class RateLimitException(
    response: HttpResponse,
    cachedResponseText: String
) : ResponseException(response = response, cachedResponseText = cachedResponseText) {
    /**
     * レート制限時の待機時間等の情報をまとめた[RateLimitData]を返す
     */
    fun getRateLimitDate(): RateLimitData {
        val retryAfter: Int? = response.headers["retry-after"]?.toInt()
        val remaining = response.headers["x-ratelimit-remaining"]?.toInt() ?: 0
        val reset: Long? = response.headers["x-ratelimit-reset"]?.toLong()
        return RateLimitData(
            retryAfter = retryAfter,
            remaining = remaining,
            reset = reset
        )
    }
}
