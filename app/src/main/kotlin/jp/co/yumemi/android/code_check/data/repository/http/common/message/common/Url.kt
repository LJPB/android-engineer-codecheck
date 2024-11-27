package jp.co.yumemi.android.code_check.data.repository.http.common.message.common

/**
 * HTTPリクエストの対象URL
 * @param protocol 通信プロトコル
 * @param host 完全修飾ドメイン名 (例 api.example.com)
 * @param parameters クエリパラメータを除いたパスのリスト (例 listOf("search", "repositories")は [host]/search/repositories に対応する
 * @param parameters クエリパラメータのリスト (例 listOf(key to value") は ?key=value に対応する
 */
data class Url(
    val protocol: UrlProtocol,
    val host: String,
    val pathSegments: List<String> = listOf(),
    val parameters: List<Pair<String, String>> = listOf()
)
