package com.ddd.pollpoll.core.data.model

import com.ddd.pollpoll.PollEndDate
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

fun PostResponse?.asExternalModel() = Post(
    categoryName = this?.categoryName ?: "",
    contents = this?.contents ?: "",
    nickname = this?.nickname ?: "",
    participantCount = this?.participantCount ?: 0,
    pollEndAt = PollEndDate(this?.pollEndAt ?: 0),
    pollId = this?.pollId ?: 0,
    pollItemCount = this?.pollItemCount ?: 0,
    postCreatedAt = this?.postCreatedAt ?: 0,
    postHits = this?.postHits ?: 0,
    postId = this?.postId ?: 0,
    pollItems = this?.pollItems?.map { it.asExternalModel() } ?: listOf(),
    title = this?.title ?: "",
    watcherCount = this?.watcherCount ?: 0,
)

fun GetPostResponse.asExternalModel() = posts.flatMap { listOf(it.asExternalModel()) }
