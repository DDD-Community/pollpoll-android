package com.ddd.pollpoll.core.network.model

import com.ddd.pollpoll.Category
import com.ddd.pollpoll.Nickname

data class GetNicknameRecommendResponse(
    val nickname: String
)

fun GetNicknameRecommendResponse.asExternalModel() = Nickname(
    nickname = this.nickname
)