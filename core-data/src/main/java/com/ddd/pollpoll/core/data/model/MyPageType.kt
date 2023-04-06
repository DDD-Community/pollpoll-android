package com.ddd.pollpoll.core.data.model

import com.ddd.pollpoll.MyPageType
import com.ddd.pollpoll.core.network.model.GetMyPageResponse

fun GetMyPageResponse.asExternalModel() = MyPageType(
    nickname,
    myPollCount,
    participatePollCount,
    watchPollCount,
    posts.map { it.asExternalModel() },
)
