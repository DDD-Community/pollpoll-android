package com.ddd.pollpoll.core.network.model

import com.ddd.pollpoll.Category

data class GetCategoriesResponse(
    val categories: List<CategoryResponse>
)

data class CategoryResponse(
    val imageUrl: String,
    val name: String
)

fun GetCategoriesResponse.asExternalModel() = this.categories.map {
    Category(it.name, it.imageUrl)
}
