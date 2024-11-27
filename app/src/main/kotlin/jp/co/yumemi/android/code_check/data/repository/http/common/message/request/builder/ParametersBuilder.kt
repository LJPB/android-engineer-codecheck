package jp.co.yumemi.android.code_check.data.repository.http.common.message.request.builder

/**
 * [HttpRequestMessageBuilder]と合わせて使う(オプション的存在)
 * これ単体では使わない
 */
interface ParametersBuilder {
    fun appendParameter(parameter: Pair<String, String>)

    fun replaceParameter(oldParameter: Pair<String, String>, newParameter: Pair<String, String>)

    fun replaceParameter(key: String, newValue: String)

    fun removeParameter(parameter: Pair<String, String>)

    fun removeParameter(key: String)

    fun removeAllParameters()
}
