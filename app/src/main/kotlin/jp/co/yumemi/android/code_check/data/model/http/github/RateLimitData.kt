package jp.co.yumemi.android.code_check.data.model.http.github

/**
 * レート制限に関する情報を表すデータクラス
 *
 * [レート制限に関するヘッダー情報](https://docs.github.com/ja/rest/using-the-rest-api/rate-limits-for-the-rest-api?apiVersion=2022-11-28#checking-the-status-of-your-rate-limit)
 * [レート制限時の処理に関する情報](https://docs.github.com/ja/rest/using-the-rest-api/best-practices-for-using-the-rest-api?apiVersion=2022-11-28#handle-rate-limit-errors-appropriately)
 * @param retryAfter リクエストを停止する秒数
 * @param remaining 現在のレート制限で残っているリクエストの数
 * @param reset [remaining]がリセットされるUTCエポック秒
 */
data class RateLimitData(
    val retryAfter: Int? = null,
    val remaining: Int? = null,
    val reset: Long? = null
)
