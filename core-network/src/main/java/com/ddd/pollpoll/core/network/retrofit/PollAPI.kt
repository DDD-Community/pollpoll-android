package com.ddd.pollpoll.core.network.retrofit

import com.ddd.pollpoll.core.network.handle.ApiResponse
import com.ddd.pollpoll.core.network.model.*
import retrofit2.Response
import retrofit2.http.*

interface PollAPI {

    @POST("/api/auth/google-login")
    suspend fun loginGoogle(@Body token: LoginRequest): Response<ApiResponse<LoginResponse>>

    @POST("/api/posts")
    suspend fun postPosts(@Body posts: PostPostRequest): Response<ApiResponse<Unit>>

    @GET("/api/posts")
    suspend fun getPosts(@Query("lastPostId") lastPostId: Int): Response<ApiResponse<GetPostResponse>>

    @GET("/api/posts/{postId}")
    suspend fun getPost(@Path("postId") postId: Int): Response<ApiResponse<PostResponse>>

    @GET("/api/posts/popular")
    suspend fun getPopularPost(): Response<ApiResponse<GetPopularResponse>>

    @PUT("/api/polls/{pollId}/participate")
    suspend fun putPoll(@Path("pollId")pollId: Int, @Body pollItemIds: PutVoteRequest): Response<ApiResponse<Unit>>
//    suspend fun putPoll(@Path("pollId")pollId: Int, @Query("pollItemIds")pollItemIds: PutVoteRequest): Response<ApiResponse<Unit>>

    @GET("/api/categories")
    suspend fun getCategories(): Response<ApiResponse<GetCategoriesResponse>>


    @GET("/api/user/nickname-recommend")
    suspend fun getNickNameRecommend(): Response<ApiResponse<GetNicknameRecommendResponse>>

    @PUT("/api/user/nickname")
    suspend fun putNickname(@Body nickname: PutNicknameRequest): Response<ApiResponse<Unit>>

    @GET("/api/user/has-nickname")
    suspend fun getHasNickname(): Response<ApiResponse<GetHasNicknameResponse>>

    @GET("/api/user/my-page")
    suspend fun getMyPage(@Query("type") type: String): Response<ApiResponse<GetMyPageResponse>>

}
