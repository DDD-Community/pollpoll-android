package com.ddd.pollpoll.designsystem.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

class PollPollColors(
    primary_500: Color,
    primary_300: Color,
    primary_100: Color,
    primary_050: Color,
    secondary_500: Color,
    secondary_300: Color,
    secondary_050: Color,
    gray_050: Color,
    gray_100: Color,
    gray_200: Color,
    gray_300: Color,
    gray_400: Color,
    gray_500: Color,
    gray_700: Color,
    gray_900: Color,
    isLight: Boolean
) {
    var primary_500 by mutableStateOf(primary_500)
        private set

    var primary_300 by mutableStateOf(primary_300)
        private set

    var primary_100 by mutableStateOf(primary_100)
        private set

    var primary_050 by mutableStateOf(primary_050)
        private set

    var secondary_500 by mutableStateOf(secondary_500)
        private set
    var secondary_300 by mutableStateOf(secondary_300)
        private set
    var secondary_050 by mutableStateOf(secondary_050)
        private set
    var gray_050 by mutableStateOf(gray_050)
        private set
    var gray_100 by mutableStateOf(gray_100)
        private set
    var gray_200 by mutableStateOf(gray_200)
        private set
    var gray_300 by mutableStateOf(gray_300)
        private set
    var gray_400 by mutableStateOf(gray_400)
        private set

    var gray_500 by mutableStateOf(gray_500)
        private set
    var gray_700 by mutableStateOf(gray_700)
        private set
    var gray_900 by mutableStateOf(gray_900)
        private set

    var isLight by mutableStateOf(isLight)
        private set

    fun copy(
        primary_500: Color = this.primary_500,
        primary_300: Color = this.primary_300,
        primary_100: Color = this.primary_100,
        primary_050: Color = this.primary_050,
        secondary_500: Color = this.secondary_500,
        secondary_300: Color = this.secondary_300,
        secondary_050: Color = this.secondary_050,
        gray_050: Color = this.gray_050,
        gray_100: Color = this.gray_100,
        gray_200: Color = this.gray_200,
        gray_300: Color = this.gray_300,
        gray_400: Color = this.gray_400,
        gray_500: Color = this.gray_500,
        gray_700: Color = this.gray_700,
        gray_900: Color = this.gray_900,
        isLight: Boolean = this.isLight
    ) = PollPollColors(
        primary_500 = primary_500,
        primary_300 = primary_300,
        primary_100 = primary_100,
        primary_050 = primary_050,
        secondary_500 = secondary_500,
        secondary_300 = secondary_300,
        secondary_050 = secondary_050,
        gray_050 = gray_050,
        gray_100 = gray_100,
        gray_200 = gray_200,
        gray_300 = gray_300,
        gray_400 = gray_400,
        gray_500 = gray_500,
        gray_700 = gray_700,
        gray_900 = gray_900,
        isLight = false
    )

    fun updateColorsFrom(other: PollPollColors) {
        primary_500 = other.primary_500
        primary_300 = other.primary_300
        primary_100 = other.primary_100
        primary_050 = other.primary_100
        secondary_500 = other.secondary_500
        secondary_300 = other.secondary_300
        secondary_050 = other.secondary_050
        gray_050 = other.gray_050
        gray_100 = other.gray_100
        gray_200 = other.gray_200
        gray_300 = other.gray_300
        gray_400 = other.gray_400
        gray_500 = other.gray_500
        gray_700 = other.gray_700
        gray_900 = other.gray_900
        isLight = false
    }
}
