package com.ddd.pollpoll.designsystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ddd.pollpoll.designsystem.theme.PollPollTheme

@Composable
internal fun Dots(
    selectWidth : Dp,
    unSelectedWidth : Dp,
    selectedColor: Color,
    unSelectedColor: Color,
    isSelected: Boolean,
    animationDurationInMillis : Int
) {
    val color: Color by animateColorAsState(targetValue = if (isSelected) selectedColor else unSelectedColor , animationSpec = tween(durationMillis = animationDurationInMillis))
    val width : Dp by animateDpAsState(targetValue = if(isSelected)selectWidth else unSelectedWidth , animationSpec = tween(durationMillis = animationDurationInMillis))


    Box(   modifier = Modifier.size(width, 10.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(color)
        .then(if(!isSelected) Modifier.border(1.5.dp, color = PollPollTheme.colors.gray_400, shape = CircleShape) else Modifier)
    )
}

@Composable
fun PollPagerIndicator(
    numberOfPages: Int,
    modifier: Modifier = Modifier,
    selectedPage: Int = 0,
    selectedColor: Color = PollPollTheme.colors.primary_500,
    unSelectedColor: Color = Color.White,
    selectWidth: Dp = 25.dp,
    unSelectedWidth: Dp = 10.dp,
    space: Dp = 10.dp,
    animationDurationInMillis: Int = 300
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space),
        modifier = modifier,
    ) {
        for (i in 0 until numberOfPages) {
            val isSelected = i == selectedPage
            Dots(
                isSelected = isSelected,
                selectedColor = selectedColor,
                unSelectedColor = unSelectedColor,
                selectWidth = selectWidth,
                unSelectedWidth = unSelectedWidth,
                animationDurationInMillis = animationDurationInMillis,
            )
        }
    }
}


@Preview
@Composable
fun DotsPreview() {
    Dots(
        selectWidth = 25.dp,
        unSelectedWidth = 10.dp,
        selectedColor = PollPollTheme.colors.primary_500,
        unSelectedColor = PollPollTheme.colors.gray_400,
        isSelected = false,
        animationDurationInMillis = 300
    )
}


@Preview
@Composable
fun PollPagerIndicatorPreview() {
    PollPagerIndicator(
        selectWidth = 25.dp,
        unSelectedWidth = 10.dp,
        selectedColor = PollPollTheme.colors.primary_500,
        unSelectedColor = PollPollTheme.colors.gray_400,
        numberOfPages = 3,
        selectedPage = 1
    )
}
