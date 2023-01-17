package com.ddd.pollpoll.core.network.retrofit

import com.ddd.pollpoll.core.network.handle.ApiResponse
import com.ddd.pollpoll.core.network.model.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PollAPI {

    @FormUrlEncoded
    @POST("/api/auth/google-login")
    suspend fun loginGoogle(@Field("idToken") token: String): ApiResponse<LoginResponse>
}
