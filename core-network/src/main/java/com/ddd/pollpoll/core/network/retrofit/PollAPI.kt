package com.ddd.pollpoll.core.network.retrofit

import com.ddd.pollpoll.core.network.handle.ApiResponse
import com.ddd.pollpoll.core.network.model.LoginRequest
import com.ddd.pollpoll.core.network.model.LoginResponse
import com.ddd.pollpoll.core.network.model.PostPostRequest
import com.ddd.pollpoll.core.network.model.PostResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface PollAPI {

    @POST("/api/auth/google-login")
    suspend fun loginGoogle(@Body token: LoginRequest): ApiResponse<LoginResponse>

    @POST("/api/posts")
    suspend fun postPosts(@Body posts: PostPostRequest): ApiResponse<PostResponse>
}
