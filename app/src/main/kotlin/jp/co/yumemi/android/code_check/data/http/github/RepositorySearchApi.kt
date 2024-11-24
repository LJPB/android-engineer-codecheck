package jp.co.yumemi.android.code_check.data.http.github

import io.ktor.client.statement.HttpResponse
import io.ktor.http.URLBuilder
import jp.co.yumemi.android.code_check.data.http.HttpClientProvider
import jp.co.yumemi.android.code_check.data.http.HttpRequestExecutor
import jp.co.yumemi.android.code_check.data.http.LinkHeaderParser
import jp.co.yumemi.android.code_check.data.http.RequestMessageBuilder
import jp.co.yumemi.android.code_check.data.structure.http.RequestMessage
import jp.co.yumemi.android.code_check.data.structure.http.UrlParameter

/**
 * GitHubのリポジトリを検索するためのクラス
 */
class RepositorySearchApi(
    private val clientProvider: HttpClientProvider,
    private val requestMessageBuilder: RequestMessageBuilder
) {
    /**
     * 検索結果が分割されていた場合、Linkヘッダから次のページなど対応するページのURLを取得するためのrel属性の値
     */
    private companion object {
        enum class LinkRelValue(val relValue: String) {
            Next("next"),
            Prev("prev"),
            First("first"),
            Last("last"),
        }
    }

    /**
     * 単語でリポジトリを検索する
     * @param word 検索ワード
     * @return 検索結果のHttpResponse
     */
    suspend fun searchWithWord(word: String): HttpResponse {
        val param = UrlParameter("q", word)
        return HttpRequestExecutor.getResponse(
            clientProvider.getClient(),
            requestMessageBuilder.appendParameter(param).build()
        )
    }

    /**
     * [urlString]に対応するHTTPレスポンスを取得する
     */
    suspend fun getResponseFromUrl(urlString: String): HttpResponse {
        val urlBuilder = URLBuilder(urlString)
        val requestMessage = RequestMessage(
            requestMethod = requestMessageBuilder.httpMethod,
            requestUrlBuilder = urlBuilder,
            requestHeaders = requestMessageBuilder.headersBuilder
        )
        return HttpRequestExecutor.getResponse(clientProvider.getClient(), requestMessage)
    }

    /**
     * 渡された[httpResponse]をから次のページのURLを取得する。次のページがなければnullを返す。
     */
    fun getNextUrl(httpResponse: HttpResponse): String? =
        getPageUrl(httpResponse, LinkRelValue.Next)

    /**
     * [httpResponse]のLinkヘッダーに[relValue]に対応するページのURLが含まれるならばそれを返し、含まれないならnullを返す
     */
    private fun getPageUrl(httpResponse: HttpResponse, relValue: LinkRelValue): String? {
        val linkHeader = httpResponse.headers["link"] ?: return null
        val url = LinkHeaderParser.getLink(linkHeader, relValue.relValue)
        return url
    }
}
