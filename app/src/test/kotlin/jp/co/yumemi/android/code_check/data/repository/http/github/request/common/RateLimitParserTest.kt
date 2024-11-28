package jp.co.yumemi.android.code_check.data.repository.http.github.request.common

import jp.co.yumemi.android.code_check.data.model.http.github.RateLimitData
import org.junit.Assert.assertEquals
import org.junit.Test

class RateLimitParserTest {
    private val retryAfter = "retry-after"
    private val remaining = "x-ratelimit-remaining"
    private val reset = "x-ratelimit-reset"

    private val expectRetryAfter = 0
    private val expectRemaining = 100
    private val expectReset = null

    private val headers = mapOf(
        retryAfter to listOf(expectRetryAfter.toString()),
        remaining to listOf(expectRemaining.toString()),
    )

    @Test
    fun getRateLimitDataTest() {
        val expectData = RateLimitData(
            retryAfter = expectRetryAfter,
            remaining = expectRemaining,
            reset = expectReset
        )
        val actualData = RateLimitParser.getRateLimitData(headers)
        assertEquals(expectData, actualData)
    }
}
