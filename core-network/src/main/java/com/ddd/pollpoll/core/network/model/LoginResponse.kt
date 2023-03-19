package com.ddd.pollpoll.core.network.model

import com.ddd.pollpoll.Login

data class LoginResponse(
    val accessToken: String
)

fun LoginResponse.asExternalModel() = Login(
    token = this.accessToken
)
