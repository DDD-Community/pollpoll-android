package com.ddd.pollpoll.core.network.model

import com.ddd.pollpoll.Category
import com.ddd.pollpoll.MyPageType
import com.ddd.pollpoll.Post

data class GetMyPageResponse(
    val nickname: String,
    val myPollCount: Int,
    val participatePollCount: Int,
    val watchPollCount: Int,
    val posts: List<Post>,
)


fun GetMyPageResponse.asExternalModel() = MyPageType(
    nickname, myPollCount, participatePollCount, watchPollCount, posts
)