package com.ddd.pollpoll.core.network.model

import com.ddd.pollpoll.Post

data class GetPostResponse(
    val posts: List<PostResponse>,
)

data class PostResponse(
    val categoryName: String,
    val contents: String,
    val nickname: String,
    val participantCount: Int,
    val pollEndAt: Long,
    val pollId: Int,
    val pollItemCount: Int,
    val postCreatedAt: Long,
    val postHits: Int,
    val postId: Int,
    val pollItems: List<PostItem>?,
    val title: String,
    val watcherCount: Int,
)

data class PostItem(
    val postItemId: Int,
    val name: String,
    val count: Int,
)

fun PostItem.asExternalModel() = com.ddd.pollpoll.PostItem(
    postItemId = this.postItemId,
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
