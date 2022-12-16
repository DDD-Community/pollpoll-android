package com.ddd.pollpoll.designsystem.theme // ktlint-disable filename

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.core_designsystem.R

val PollPollFamily = FontFamily(
    Font(R.font.pretendard_regular, FontWeight.Normal),
    Font(R.font.pretendard_semibold, FontWeight.SemiBold)
)

enum class TypoStyle(val textSize: Int, val fontWeight: FontWeight, val lineHeight: Int = 0) {
    // 상단
    HEADING_01(32, FontWeight.SemiBold),
    HEADING_02(28, FontWeight.SemiBold),
    HEADING_03(24, FontWeight.SemiBold),
    HEADING_04(20, FontWeight.SemiBold),
    HEADING_05(18, FontWeight.SemiBold),

    // 본문
    BODY_01(16, FontWeight.Normal),
    BODY_02(14, FontWeight.Normal),
    BODY_03(12, FontWeight.Normal),

    // 설명
    DESCRIPTION(10, FontWeight.Normal)
}
