package com.ddd.pollpoll.feature.vote

import androidx.annotation.DrawableRes
import com.ddd.pollpoll.CategoryEnum
import com.ddd.pollpoll.designsystem.icon.PollIcon

data class Category(val categoryEnum: CategoryEnum, @DrawableRes val iconDrawable: Int, val text: String)

val CategoryList = listOf<Category>(
    Category(CategoryEnum.Buy, PollIcon.Buy, "구매"),
    Category(CategoryEnum.Carrier, PollIcon.Carrier, "이직"),
    Category(CategoryEnum.Carrier2, PollIcon.Carrier2, "취업"),
    Category(CategoryEnum.Love, PollIcon.Love, "연애"),
    Category(CategoryEnum.Group, PollIcon.Group, "호불호"),
    Category(CategoryEnum.Worry, PollIcon.Worry, "고민")
)

internal val defalutCategory = Category(CategoryEnum.Buy, PollIcon.Buy, "구매")

fun CategoryEnum.toCategory() = CategoryList.first() { this == it.categoryEnum }