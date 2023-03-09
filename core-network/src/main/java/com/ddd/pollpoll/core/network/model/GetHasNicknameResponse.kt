package com.ddd.pollpoll.core.network.model

import com.ddd.pollpoll.HasNickname
import com.ddd.pollpoll.Nickname

data class GetHasNicknameResponse(
    val hasNickname: Boolean
)

fun GetHasNicknameResponse.asExternalModel() = HasNickname(
    hasNickname = this.hasNickname
)