package com.ddd.pollpoll.core.network.model

data class GetCategoriesResponse(
    val categories: List<CategoryResponse>,
)

data class CategoryResponse(
    val categoryId: Int,
    val imageUrl: String,
    val name: String,
)
