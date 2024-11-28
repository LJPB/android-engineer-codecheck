package jp.co.yumemi.android.code_check.ui.screen.github.repository.detail

import android.net.Uri
import android.os.Bundle
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.os.BundleCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.window.core.layout.WindowWidthSizeClass
import jp.co.yumemi.android.code_check.data.model.http.github.RepositoryDetail
import jp.co.yumemi.android.code_check.ui.component.github.repository.detail.RepositoryDetailScreenContent
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

@Composable
fun RepositoryDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: RepositoryDetailViewModel,
    windowWidthSizeClass: WindowWidthSizeClass
) {
    RepositoryDetailScreenContent(
        modifier = modifier,
        repositoryDetail = viewModel.repositoryDetail,
        windowWidthSizeClass = windowWidthSizeClass
    )
}

fun NavGraphBuilder.repositoryDetailScreen() {
    composable<RepositoryDetailDestination>(
        typeMap = mapOf(typeOf<RepositoryDetail>() to RepositoryDetailNavType)
    ) {
        RepositoryDetailScreen(
            viewModel = viewModel(),
            windowWidthSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
        )
    }
}

@Serializable
data class RepositoryDetailDestination(val detail: RepositoryDetail)

// RepositoryDetailをNavArgで受け取るために必要な処置
val RepositoryDetailNavType = object : NavType<RepositoryDetail>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): RepositoryDetail {
        return requireNotNull(
            BundleCompat.getParcelable(
                bundle,
                key,
                RepositoryDetail::class.java
            )
        )
    }

    override fun put(bundle: Bundle, key: String, value: RepositoryDetail) {
        bundle.putParcelable(key, value)
    }

    override fun serializeAsValue(value: RepositoryDetail): String {
        val uri = Uri.encode(Json.encodeToString(value))
        return uri
    }

    override fun parseValue(value: String): RepositoryDetail {
        return Json.decodeFromString(value)
    }
}
