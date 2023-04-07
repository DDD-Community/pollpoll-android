package com.ddd.pollpoll.core.data.model

import com.ddd.pollpoll.Login
import com.ddd.pollpoll.core.network.model.LoginResponse

fun LoginResponse.asExternalModel() = Login(
    token = this.accessToken
)
