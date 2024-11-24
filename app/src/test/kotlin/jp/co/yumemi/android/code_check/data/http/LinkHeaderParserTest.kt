package jp.co.yumemi.android.code_check.data.http

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Test

class LinkHeaderParserTest {
    private val nextUrl = "https://example.com/next"
    private val nextRelValue = "next"

    private val prevUrl = "https://example.com/prev"
    private val prevRelValue = "prev"

    private val linkHeaderString =
        "<$nextUrl>; rel=\"$nextRelValue\", <$prevUrl>; rel=\"$prevRelValue\""

    /**
     * URLを正しく取得できるかのテスト
     */
    @Test
    fun getUrlTest() {
        val actualNextUrl = LinkHeaderParser.getLink(linkHeaderString, nextRelValue)
        val actualPrevUrl = LinkHeaderParser.getLink(linkHeaderString, prevRelValue)
        println(nextUrl)
        assertEquals(nextUrl, actualNextUrl)
        println(prevUrl)
        assertEquals(prevUrl, actualPrevUrl)
    }

    /**
     * 存在しない属性値ではちゃんとnullが返ってくるかのテスト
     */
    @Test
    fun getUrlTestNotFound() {
        val actualUrl = LinkHeaderParser.getLink(linkHeaderString, nextRelValue + prevRelValue)
        assertNull(actualUrl)
    }

    // 網羅していない
    /**
     * 不正な形式のLinkヘッダーから取得しようとするとちゃんとnullが返ってくるかのテスト
     */
    @Test
    fun getUrlTestBrokenHeader() {
        val brokenLinkHeader = "a,<a>;"
        val actualUrl = LinkHeaderParser.getLink(brokenLinkHeader, "a")
        assertNull(actualUrl)
    }

    /**
     * URLが不正な形式の場合にちゃんとnullが返ってくるかのテスト
     */
    @Test
    fun getUrlTestInvalidUrl() {
        val brokenLinkHeader = "<https://a.a>; rel=\"$nextRelValue\""
        val actualUrl = LinkHeaderParser.getLink(brokenLinkHeader, nextRelValue)
        assertNull(actualUrl)
    }

}
