package jp.co.yumemi.android.code_check.ui.component.repository_search

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.model.http.github.RateLimitData
import jp.co.yumemi.android.code_check.ui.component.common.CenterColumn
import jp.co.yumemi.android.code_check.ui.component.common.CenterContainer
import java.time.LocalDateTime
import java.time.ZoneOffset

/**
 * レート制限時に表示する画面
 */
@Composable
fun RateLimitScreen(
    modifier: Modifier = Modifier,
    rateLimitData: RateLimitData
) {
    // レート制限が解除される日時
    val resetDateTime = if (rateLimitData.reset != null) {
        LocalDateTime.ofEpochSecond(
            rateLimitData.reset,
            0,
//            ZoneOffset.of(ZoneOffset.systemDefault().toString())
            ZoneOffset.UTC
        ).toString()
    } else {
        ""
    }

    val context = LocalContext.current
    val url = stringResource(R.string.rateLimitReferenceUrl)
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

    CenterContainer(modifier = modifier.fillMaxSize()) {
        CenterColumn {
            Text(
                color = MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.Bold,
                text = stringResource(R.string.rateLimit)
            )
            Text(
                text = stringResource(
                    R.string.rateLimitRetryAfter,
                    rateLimitData.retryAfter?.toString() ?: ""
                )
            )
            Text(
                text = stringResource(
                    R.string.rateLimitRemaining,
                    rateLimitData.remaining?.toString() ?: ""
                )
            )
            Text(
                text = stringResource(R.string.rateLimitReset, resetDateTime)
            )
            Text(
                modifier = Modifier.clickable {
                    context.startActivity(intent) // 参考情報へ遷移
                },
                color = MaterialTheme.colorScheme.primary,
                text = stringResource(R.string.rateLimitReference)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RateLimitPreview() {
    RateLimitScreen(
        modifier = Modifier,
        rateLimitData = RateLimitData()
    )
}
