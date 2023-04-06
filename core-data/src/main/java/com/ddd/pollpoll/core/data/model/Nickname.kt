package com.ddd.pollpoll.core.data.model

import com.ddd.pollpoll.HasNickname
import com.ddd.pollpoll.Nickname
import com.ddd.pollpoll.core.network.model.GetHasNicknameResponse
import com.ddd.pollpoll.core.network.model.GetNicknameRecommendResponse

fun GetNicknameRecommendResponse.asExternalModel() = Nickname(
    nickname = this.nickname,
)

fun GetHasNicknameResponse.asExternalModel() = HasNickname(
    hasNickname = this.hasNickname
)