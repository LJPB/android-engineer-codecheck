package jp.co.yumemi.android.code_check.data.http

object LinkHeaderParser {
    // URLとrelの組を扱いやすくするためのもの
    private data class LinkHeader(val url: String, val rel: String)

    /**
     * Linkヘッダーから[relVal]に対応するURLを返す
     * @param linkHeaderString Linkヘッダーの文字列
     * @param relVal URLを取り出したい属性値 (rel="value"の場合value)
     * @return 対応するURL。存在しない場合はnullを返す。
     */
    fun getLink(linkHeaderString: String, relVal: String): String? {
        val linkList = parseLinkHeaderToList(linkHeaderString)
        linkList.forEach {
            if (it.rel == "rel=\"$relVal\"") return it.url
        }
        return null
    }

    /**
     * LinkヘッダーからURLとrel値の組からなる[LinkHeader]のリストを返す
     * @param linkHeaderString Linkヘッダーの文字列
     * @return [LinkHeader]のリスト。不正な形式だったりLinkヘッダーが存在しない場合は空のリストを返す。
     */
    private fun parseLinkHeaderToList(linkHeaderString: String): List<LinkHeader> {
        // rel属性ごとに区切っている
        val linkList = linkHeaderString
            .filterNot { it.isWhitespace() }  // 処理を簡単にするために全ての空白文字を除去している
            .split(",")
        val linkHeaderList: MutableList<LinkHeader> = mutableListOf()
        linkList.forEach {
            val tmpList = it.split(";") // 文字列 "<URL>; rel" を <URL>とrelの組(リスト)になおしている

            if (tmpList.size != 2) return@forEach // この場合<URL>とrelの組になっていないからスキップ

            val urlTmp = tmpList[0] // <>で囲まれたURL
            val rel = tmpList[1]

            val regex = Regex("""<([^>]+)>""") // <URL>から<>を取り除くためのパターン
            val match = regex.find(urlTmp) ?: return@forEach // URLを取得できなければスキップ
            val url = match.groups[1]?.value ?: return@forEach // URLを取得できなければスキップ
            linkHeaderList.add(LinkHeader(url, rel))
        }
        return linkHeaderList.toList()
    }
}
