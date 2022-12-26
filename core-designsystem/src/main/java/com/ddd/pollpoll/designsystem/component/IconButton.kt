package com.ddd.pollpoll.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddd.pollpoll.designsystem.icon.PollIcon
import com.ddd.pollpoll.designsystem.theme.PollPollTheme
import com.ddd.pollpoll.designsystem.theme.Shadow

@Composable
fun PollIconButtonText(
    @DrawableRes iconRes: Int = PollIcon.Love,
    contentDes: String? = null,
    isClicked: Boolean = false,
    onClick: () -> Unit = {},
    text: String = "구매"
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        PollIconButton(iconRes, contentDes, isClicked, onClick = onClick)
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Text(text = text, style = PollPollTheme.typography.heading05)
    }
}

@Composable
fun PollIconButton(
    @DrawableRes iconRes: Int = PollIcon.Love,
    contentDes: String? = null,
    isClicked: Boolean = false,
    onClick: () -> Unit = {}
) {
    OutlinedIconButton(
        modifier = Modifier.size(72.dp).drawBehind {
            val width = size.width
            val height = size.height
            val center = size.center

            drawIntoCanvas {
                val paint = Paint().asFrameworkPaint().apply {
                    setShadowLayer(10.dp.toPx(), 0.dp.toPx(), 0.dp.toPx(), Shadow.toArgb())
                }
                val canvas = it.nativeCanvas
                canvas.drawCircle(center.x, center.y, center.x, paint)
            }
        },
        onClick = { onClick },
        colors = IconButtonDefaults.outlinedIconButtonColors(
            containerColor = if (isClicked) PollPollTheme.colors.primary_100 else Color.White

        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (isClicked) PollPollTheme.colors.primary_500 else Color.White
        )

    ) {
        Image(painter = painterResource(id = iconRes), contentDescription = contentDes)
    }
}

@Preview
@Composable
fun PollIconButtonPreview() {
    PollPollTheme() {
        PollIconButton()
    }
}

@Preview
@Composable
fun PollIconButtonEnabledPreview() {
    PollPollTheme() {
        PollIconButton(isClicked = true)
    }
}

@Preview
@Composable
fun PollIconButtonTextPreview() {
    PollPollTheme() {
        PollIconButtonText(isClicked = true)
    }
}
