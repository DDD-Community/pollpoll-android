package com.ddd.pollpoll.core.network.retrofit

import com.ddd.pollpoll.core.network.handle.ApiResponse
import com.ddd.pollpoll.core.network.model.LoginRequest
import com.ddd.pollpoll.core.network.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface PollAPI {

    @POST("/api/auth/google-login")
    suspend fun loginGoogle(@Body token: LoginRequest): ApiResponse<LoginResponse>
}
