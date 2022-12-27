package com.ddd.pollpoll.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun lightColors() = PollPollColors(
    primary_500 = Primary500,
    primary_300 = Primary300,
    primary_100 = Primary100,
    primary_050 = Primary050,
    secondary_500 = Secondary500,
    secondary_300 = Secondary300,
    secondary_050 = Secondary050,
    gray_050 = Gray050,
    gray_100 = Gray100,
    gray_200 = Gray200,
    gray_300 = Gray300,
    gray_400 = Gray400,
    gray_500 = Gray500,
    gray_700 = Gray700,
    gray_900 = Gray900,
    isLight = false

)

// fun darkColors() = PollPollColors(
//    primary = Color(0xFFDF6900),
//    text = Color(0xFFFFFFFF),
//    background = Color(0xFF353B48),
//    success = Color(0xFF44BD32),
//    error = Color(0xFFC23616),
//    isLight = false
// )

object PollPollTheme {
    val colors: PollPollColors
        @Composable @ReadOnlyComposable
        get() = LocalColors.current

    val typography: CustomTypography
        @Composable @ReadOnlyComposable
        get() = LocalTypography.current

    val spaces: CustomSpaces
        @Composable @ReadOnlyComposable
        get() = LocalSpaces.current
}

data class CustomSpaces(
    val small: Dp = 4.dp,
    val medium: Dp = 8.dp,
    val large: Dp = 16.dp,
    val extraLarge: Dp = 40.dp
)

@Composable
fun PollPollTheme(
    spaces: CustomSpaces = PollPollTheme.spaces,
    typography: CustomTypography = PollPollTheme.typography,
    colors: PollPollColors = PollPollTheme.colors,
    darkColors: PollPollColors? = null,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val currentColor = remember { if (darkColors != null && darkTheme) darkColors else colors }
    val rememberedColors = remember { currentColor.copy() }.apply { updateColorsFrom(currentColor) }

    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        LocalSpaces provides spaces,
        LocalTypography provides typography
    ) {
        ProvideTextStyle(typography.heading05, content = content)
    }
}

val LocalSpaces = staticCompositionLocalOf { CustomSpaces() }
val LocalColors = staticCompositionLocalOf { lightColors() }
