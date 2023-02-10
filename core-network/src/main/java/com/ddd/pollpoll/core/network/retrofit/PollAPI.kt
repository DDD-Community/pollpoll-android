package com.ddd.pollpoll.core.network.retrofit

import com.ddd.pollpoll.core.network.handle.ApiResponse
import com.ddd.pollpoll.core.network.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PollAPI {

    @POST("/api/auth/google-login")
    suspend fun loginGoogle(@Body token: LoginRequest): Response<ApiResponse<LoginResponse>>

    @POST("/api/posts")
    suspend fun postPosts(@Body posts: PostPostRequest): Response<ApiResponse<Unit>>

    @GET("/api/posts")
    suspend fun getPosts(@Query("lastPostId")lastPostId: Int): Response<ApiResponse<GetPostResponse>>

    @GET("/api/posts/{postId}")
    suspend fun getPost(@Path("postId")postId: Int): Response<ApiResponse<PostResponse>>

    @GET("/api/categories")
    suspend fun getCategories(): Response<ApiResponse<GetCategoriesResponse>>
}
