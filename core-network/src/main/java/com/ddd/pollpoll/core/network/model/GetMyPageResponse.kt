package com.ddd.pollpoll.core.network.model

import com.ddd.pollpoll.MyPage
import com.ddd.pollpoll.PopularPost

data class GetMyPageResponse(
    val nickname: String,
    val myPollCount: Long,
    val participatePollCount: Long,
    val watchPollCount: Long,
    val posts: List<PostResponse>
)

fun GetMyPageResponse.asExternalModel() = MyPage(
    nickname = this.nickname,
    myPollCount = this.myPollCount,
    participatePollCount = this.participatePollCount,
    watchPollCount = this.watchPollCount,
    posts = this.posts.map { it.asExternalModel() }
)

