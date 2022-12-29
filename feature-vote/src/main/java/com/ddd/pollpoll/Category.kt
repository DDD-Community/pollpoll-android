package com.ddd.pollpoll

import androidx.annotation.DrawableRes
import com.ddd.pollpoll.designsystem.icon.PollIcon

data class Category(@DrawableRes val iconDrawable: Int, val text: String)

val CategoryList = listOf<Category>(
    Category(PollIcon.Buy, "구매"),
    Category(PollIcon.Carrier, "이직"),
    Category(PollIcon.Carrier2, "취업"),
    Category(PollIcon.Love, "연애"),
    Category(PollIcon.Group, "호불호"),
    Category(PollIcon.Worry, "고민")
)
