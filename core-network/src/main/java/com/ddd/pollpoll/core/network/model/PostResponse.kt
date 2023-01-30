package com.ddd.pollpoll.core.network.model

data class PostResponse(
    val categoryName: String,
    val contents: String,
    val nickname: String,
    val participantCount: Int,
    val pollEndAt: Int,
    val pollId: Int,
    val pollItemCount: Int,
    val postCreatedAt: Int,
    val postHits: Int,
    val postId: Int,
    val title: String,
    val watcherCount: Int
)