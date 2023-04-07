package com.ddd.pollpoll.core.data.model

import com.ddd.pollpoll.Category
import com.ddd.pollpoll.core.network.model.GetCategoriesResponse

fun GetCategoriesResponse.asExternalModel() = this.categories.map {
    Category(it.name, it.imageUrl)
}
