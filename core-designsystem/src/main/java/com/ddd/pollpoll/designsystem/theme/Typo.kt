package com.ddd.pollpoll.designsystem.theme // ktlint-disable filename

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.core_designsystem.R

val PollPollFamily = FontFamily(
    Font(R.font.pretendard_regular, FontWeight.Normal),
    Font(R.font.pretendard_semibold, FontWeight.SemiBold)
)

enum class PollTypoStyle(val textSize: Int, val fontWeight: FontWeight, val lineHeight: Int = 0) {
    // 상단
    HEADING_01(28, FontWeight.SemiBold, 38),
    HEADING_02(24, FontWeight.SemiBold, 32),
    HEADING_03(20, FontWeight.SemiBold, 26),
    HEADING_04(18, FontWeight.SemiBold, 26),
    HEADING_05(16, FontWeight.SemiBold, 22),

    // 본문
    BODY_01(16, FontWeight.Normal, 26),
    BODY_02(14, FontWeight.Normal, 22),
    BODY_03(12, FontWeight.Normal, 20),
    BODY_04(12, FontWeight.Normal, 18),

    // 설명
    DESCRIPTION(10, FontWeight.Normal)
}

data class CustomTypography(
    val heading01: TextStyle = TextStyle(
        fontFamily = PollPollFamily,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 38.sp,
        fontSize = 28.sp
    ),
    val heading02: TextStyle = TextStyle(
        fontFamily = PollPollFamily,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 32.sp,
        fontSize = 24.sp
    ),

    val heading03: TextStyle = TextStyle(
        fontFamily = PollPollFamily,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 26.sp,
        fontSize = 20.sp
    ),
    val heading04: TextStyle = TextStyle(
        fontFamily = PollPollFamily,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 26.sp,
        fontSize = 18.sp
    ),

    val heading05: TextStyle = TextStyle(
        fontFamily = PollPollFamily,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 22.sp,
        fontSize = 16.sp
    ),
    val body01: TextStyle = TextStyle(
        fontFamily = PollPollFamily,
        fontWeight = FontWeight.Normal,
        lineHeight = 26.sp,
        fontSize = 18.sp
    ),

    val body02: TextStyle = TextStyle(
        fontFamily = PollPollFamily,
        fontWeight = FontWeight.Normal,
        lineHeight = 22.sp,
        fontSize = 16.sp
    ),
    val body03: TextStyle = TextStyle(
        fontFamily = PollPollFamily,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
        fontSize = 14.sp
    ),
    val body04: TextStyle = TextStyle(
        fontFamily = PollPollFamily,
        fontWeight = FontWeight.Normal,
        lineHeight = 18.sp,
        fontSize = 12.sp
    ),
    val desc: TextStyle = TextStyle(
        fontFamily = PollPollFamily,
        fontWeight = FontWeight.Normal,
        lineHeight = 18.sp,
        fontSize = 10.sp
    )

)

val LocalTypography = staticCompositionLocalOf { CustomTypography() }
