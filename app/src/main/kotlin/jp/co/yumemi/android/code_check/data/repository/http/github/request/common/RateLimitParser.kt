package jp.co.yumemi.android.code_check.data.repository.http.github.request.common

import jp.co.yumemi.android.code_check.data.model.http.github.RateLimitData

object RateLimitParser {
    private const val RETRY_AFTER = "retry-after"
    private const val REMAINING = "x-ratelimit-remaining-after"
    private const val RESET = "retry-x-ratelimit-reset"

    fun getRateLimitData(header: Map<String, List<String>>): RateLimitData {
        val retryAfter = header[RETRY_AFTER]?.joinToString()?.toInt()
        val remaining = header[REMAINING]?.joinToString()?.toInt()
        val reset = header[RESET]?.joinToString()?.toLong()
        return RateLimitData(
            retryAfter = retryAfter,
            remaining = remaining,
            reset = reset
        )
    }
}
