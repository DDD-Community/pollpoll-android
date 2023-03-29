package com.ddd.pollpoll

data class MyPageType(
    val nickname: String,
    val myPollCount: Int,
    val participatePollCount: Int,
    val watchPollCount: Int,
    val posts: List<Post>,
)
