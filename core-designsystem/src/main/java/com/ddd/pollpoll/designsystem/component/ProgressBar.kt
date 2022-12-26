package com.ddd.pollpoll.designsystem.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.progressSemantics
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddd.pollpoll.designsystem.theme.PollPollTheme

@Composable
fun PollProgressBar(
    progress: Float = 0.5f,
    color: androidx.compose.ui.graphics.Color = PollPollTheme.colors.primary_500,
    trackColor: androidx.compose.ui.graphics.Color = PollPollTheme.colors.gray_300
) {
    require(progress < 1f && progress > 0f, { "해당 프로그레스 바는 0f과 1f 사이여야합니다." })
    val animatedProgress: Float by animateFloatAsState(targetValue = progress)
    Canvas(
        Modifier
            .progressSemantics()
            .fillMaxWidth()
            .height(2.dp)
    ) {
        val strokeWidth = size.width
        val strokeHeight = size.height

        val endFraction = animatedProgress * strokeWidth
        drawLine(
            color = trackColor,
            start = Offset(0f, strokeHeight),
            end = Offset(strokeWidth, strokeHeight),
            strokeWidth = strokeHeight
        )

        drawLine(
            color = color,
            start = Offset(0f, strokeHeight),
            end = Offset(endFraction, strokeHeight),
            strokeWidth = strokeHeight
        )
    }
}

@Preview
@Composable
fun PollProgressBarPreview() {
    PollPollTheme() {
        PollProgressBar()
    }
}
