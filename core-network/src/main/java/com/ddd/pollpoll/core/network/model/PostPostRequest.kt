package com.ddd.pollpoll.core.network.model

import com.ddd.pollpoll.Vote

data class PostPostRequest(
    val categoryId: Int,
    val contents: String,
    val milliseconds: Long,
    val multipleChoice: Boolean,
    val pollItems: List<PollItem>,
    val title: String
)

data class PollItem(
    val name: String
)

fun Vote.asNetworkModel() = PostPostRequest(
    categoryId = this.category.categoryId,
    contents = this.contents,
    milliseconds = this.milliseconds,
    multipleChoice = this.multipleChoice,
    pollItems = this.pollItems.map { it.asNetworkModel() },
    title = title

)

fun com.ddd.pollpoll.PollItem.asNetworkModel() = PollItem(
    name = name
)
