package com.ddd.pollpoll.core.network.retrofit

import com.ddd.pollpoll.core.network.handle.ApiResponse
import com.ddd.pollpoll.core.network.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PollAPI {

    @POST("/api/auth/google-login")
    suspend fun loginGoogle(@Body token: LoginRequest): ApiResponse<LoginResponse>

    @POST("/api/posts")
    suspend fun postPosts(@Body posts: PostPostRequest): ApiResponse<PostResponse>

    @GET("/api/posts")
    suspend fun getPosts(@Query("lastPostId")lastPostId: Int): ApiResponse<GetPostResponse>

    @GET("/api/categories")
    suspend fun getCategories(): ApiResponse<GetCategoriesResponse>
}
