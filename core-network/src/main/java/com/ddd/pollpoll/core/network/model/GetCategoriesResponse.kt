package com.ddd.pollpoll.core.network.model

data class GetCategoriesResponse(
    val categories: List<Category>
)

data class Category(
    val imageUrl: String,
    val name: String
)