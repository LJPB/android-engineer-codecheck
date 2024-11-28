package jp.co.yumemi.android.code_check.data.repository.http.github.request.repository

import jp.co.yumemi.android.code_check.data.model.http.github.RepositorySearchResponse
import jp.co.yumemi.android.code_check.data.repository.http.common.message.response.HttpResponseMessage

/**
 * リポジトリーを検索する時に指定できるクエリパラメータをまとめたオブジェクト
 */
object RepositorySearchQueryType {
    /**
     * 検索ワードを渡すクエリ
     */
    object SearchWord {
        /**
         * クエリパラメータのキーに対応する文字列
         */
        const val KEY = "q"
    }

    /**
     * 1ページあたりのリポジトリ数を指定するクエリ
     */
    object PerPage {
        /**
         * クエリパラメータのキーに対応する文字列
         */
        const val KEY = "per_page"
    }


    /**
     * ページ数を指定するクエリ
     */
    object Page {
        /**
         * クエリパラメータのキーに対応する文字列
         */
        const val KEY = "page"
    }

    /**
     * ソートの基準を指定するクエリ
     * @param value URLに付与するクエリパラメータの値
     */
    sealed class Sort(val value: String) {
        /**
         * クエリパラメータのキーに対応する文字列
         */
        val key = "sort"

        data object Stars : Sort("stars")
        data object Forks : Sort("forks")
        data object HelpWantedIssues : Sort("help_wanted_issues")
        data object Updated : Sort("updated")
    }

    /**
     * 並び順を指定するクエリ
     * デフォルトはdescだがソートが設定されていない場合は無効
     * @param value URLに付与するクエリパラメータの値
     */
    sealed class Order(val value: String) {
        /**
         * クエリパラメータのキーに対応する文字列
         */
        val key = "order"

        data object Desc : Sort("desc")
        data object Asc : Sort("asc")
    }
}

interface GitHubRepositorySearchService {
    /**
     * 検索する
     * @param word 検索ワード
     */
    suspend fun search(word: String): HttpResponseMessage<RepositorySearchResponse>

    /**
     * 検索結果を並び替えるクエリの設定
     */
    fun sortBy(type: RepositorySearchQueryType.Sort)

    /**
     * 検索結果の並び順を指定するクエリの設定
     */
    fun orderBy(type: RepositorySearchQueryType.Order)

    /**
     * 1ページあたりに表示するアイテム数を指定するクエリの設定
     */
    fun prePage(number: Int)

    /**
     * ページ番号を指定するクエリの設定
     */
    fun pageOf(number: Int)
}
