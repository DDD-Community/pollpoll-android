package com.ddd.pollpoll.core.network.model

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
    val isPolled: Boolean,
    val pollItemId: Int,
    val name: String,
    val count: Int,
)
