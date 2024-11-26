package jp.co.yumemi.android.code_check.data.util

import android.content.Context
import android.net.ConnectivityManager

object NetworkState {
    fun isActiveNetwork(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null
    }
}
