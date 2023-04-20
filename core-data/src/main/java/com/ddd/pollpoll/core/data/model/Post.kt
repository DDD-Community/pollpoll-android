package com.ddd.pollpoll.core.data.model

import com.ddd.pollpoll.Post
import com.ddd.pollpoll.core.network.model.GetPostResponse
import com.ddd.pollpoll.core.network.model.PostItem
import com.ddd.pollpoll.core.network.model.PostResponse

fun PostItem.asExternalModel() = com.ddd.pollpoll.PostItem(
    isPolled = this.isPolled,
    pollItemId = this.pollItemId,
    name = this.name,
    count = this.count,

)

fun PostResponse.asExternalModel() = Post(
    categoryName = this.categoryName,
    contents = this.contents,
    nickname = this.nickname,
    participantCount = this.participantCount,
    pollEndAt = this.pollEndAt,
    pollId = this.pollId,
    pollItemCount = this.pollItemCount,
    postCreatedAt = this.postCreatedAt,
    postHits = this.postHits,
    postId = this.postId,
    pollItems = this.pollItems?.map { it.asExternalModel() } ?: listOf(),
    title = title,
    watcherCount = watcherCount,
)

fun GetPostResponse.asExternalModel() = posts.flatMap { listOf(it.asExternalModel()) }
