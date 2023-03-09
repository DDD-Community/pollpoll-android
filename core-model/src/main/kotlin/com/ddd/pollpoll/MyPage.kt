package com.ddd.pollpoll

data class MyPage(
    val nickname: String,
    val myPollCount: Long,
    val participatePollCount: Long,
    val watchPollCount: Long,
    val posts: List<Post>
)


