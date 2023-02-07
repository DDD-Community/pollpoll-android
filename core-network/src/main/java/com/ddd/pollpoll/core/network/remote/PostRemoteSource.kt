package com.ddd.pollpoll.core.network.remote

import com.ddd.pollpoll.core.network.handle.executeHandle
import com.ddd.pollpoll.core.network.model.GetPostResponse
import com.ddd.pollpoll.core.network.model.PostPostRequest
import com.ddd.pollpoll.core.network.model.PostResponse
import com.ddd.pollpoll.core.network.retrofit.PollAPI
import javax.inject.Inject

interface PostRemoteSource {

    suspend fun insertPost(post: PostPostRequest): PostResponse
    suspend fun getPosts(lastPostId: Int): GetPostResponse
}

class PostRemoteSourceImp @Inject constructor(
    private val pollAPI: PollAPI
) : PostRemoteSource {
    override suspend fun insertPost(post: PostPostRequest): PostResponse = pollAPI.postPosts(post).executeHandle()
    override suspend fun getPosts(lastPostId: Int): GetPostResponse = pollAPI.getPosts(lastPostId).executeHandle()
}
