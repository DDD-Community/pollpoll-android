package com.ddd.pollpoll.core.network.model

data class PostPostRequest(
    val categoryId: Int,
    val contents: String,
    val milliseconds: Long,
    val multipleChoice: Boolean,
    val pollItems: List<PollItem>,
    val title: String,
)

data class PollItem(
    val name: String,
)
