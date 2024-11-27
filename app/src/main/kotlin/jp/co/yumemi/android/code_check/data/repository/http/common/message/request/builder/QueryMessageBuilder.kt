package jp.co.yumemi.android.code_check.data.repository.http.common.message.request.builder

import jp.co.yumemi.android.code_check.data.repository.http.common.message.request.HttpRequestMessage

/**
 * クエリパラメータの設定ができる[HttpRequestMessage]のビルダークラス
 * @param defaultMessage 不変のデフォルトメッセージ このメッセージに対してクエリパラメータを追加する
 */
class QueryMessageBuilder(override val defaultMessage: HttpRequestMessage) :
    HttpRequestMessageBuilder, ParametersBuilder {
    private var appendedParameters: MutableList<Pair<String, String>> = mutableListOf()

    /**
     * [HttpRequestMessage]を出力する
     * buildした後に明示的に[clear]メソッドを呼ばないと追加したクエリパラメータはリセットされない
     */
    override fun build(): HttpRequestMessage {
        val url = defaultMessage.url.let {
            it.copy(parameters = it.parameters + appendedParameters)
        }
        return HttpRequestMessage(
            httpMethod = defaultMessage.httpMethod,
            url = url,
            headers = defaultMessage.headers
        )
    }

    /**
     * 追加したクエリパラメータをリセットする
     */
    override fun clear() {
        removeAllParameters()
    }

    /**
     * クエリパラメータをリクエストURLに追加する
     * @param parameter 追加するパラメータの組 ?first=second の関係
     */
    override fun appendParameter(parameter: Pair<String, String>) {
        appendedParameters.add(parameter)
    }

    /**
     * 追加したクエリパラメータの[oldParameter]に一致する全てのパラメータを[newParameter]に置き換える
     */
    override fun replaceParameter(
        oldParameter: Pair<String, String>,
        newParameter: Pair<String, String>
    ) {
        appendedParameters = appendedParameters
            .map { if (it == oldParameter) newParameter else it }
            .toMutableList()
//        appendedParameters.replaceAll { if (it == oldParameter) newParameter else it }
    }

    /**
     * 追加したクエリパラメータの[key]に対応する全ての値を[newValue]に置き換える (?key=oldValue なら ?key=newValue になる)
     */
    override fun replaceParameter(key: String, newValue: String) {
        appendedParameters = appendedParameters
            .map { if (it.first == key) Pair(key, newValue) else it }
            .toMutableList()
//        appendedParameters.replaceAll { if (it.first == key) Pair(key, newValue) else it }
    }

    /**
     * 追加したクエリパラメータの[parameter]と一致するパラメータを全て削除する
     */
    override fun removeParameter(parameter: Pair<String, String>) {
        appendedParameters = appendedParameters
            .filter { it != parameter }
            .toMutableList()
//        appendedParameters.removeIf { it == parameter }
    }

    /**
     * 追加したクエリパラメータの[key]と一致するパラメータを全て削除する
     */
    override fun removeParameter(key: String) {
        appendedParameters = appendedParameters
            .filter { it.first != key }
            .toMutableList()
//        appendedParameters.removeIf { it.first == key }
    }

    /**
     * 追加したパラメータを全て削除する
     */
    override fun removeAllParameters() {
        appendedParameters = mutableListOf()
    }
}
