package com.ddd.pollpoll.core.network.model

data class GetMyPageResponse(
    val nickname: String,
    val myPollCount: Int,
    val participatePollCount: Int,
    val watchPollCount: Int,
    val posts: List<PostResponse>,
)


