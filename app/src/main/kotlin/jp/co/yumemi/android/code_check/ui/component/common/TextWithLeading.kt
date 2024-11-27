package jp.co.yumemi.android.code_check.ui.component.common

import androidx.collection.intIntMapOf
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun TextWithLeading(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    style: TextStyle = LocalTextStyle.current,
    leadingContent: @Composable () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        leadingContent()
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = color,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            minLines = minLines,
            onTextLayout = onTextLayout,
            style = style
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TextWithLeadingPreview() {
    TextWithLeading(
        modifier = Modifier,
        text = "text"
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null
        )
    }
}

/**
 * leadingContentが大きいバージョン
 */
@Preview(showBackground = true)
@Composable
private fun TextWithLeadingBigContentPreview() {
    TextWithLeading(
        modifier = Modifier,
        text = "text"
    ) {
        Column {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null
            )
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null
            )
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null
            )
        }
    }
}

/**
 * 文字が多いバージョン
 */
@Preview(showBackground = true)
@Composable
private fun TextWithLeadingBigTextPreview() {
    TextWithLeading(
        modifier = Modifier,
        text = (1..100).joinToString { "text" }
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null
        )
    }
}
