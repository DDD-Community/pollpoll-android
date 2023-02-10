package com.ddd.pollpoll.core.network.remote

import com.ddd.pollpoll.core.network.handle.executeHandle
import com.ddd.pollpoll.core.network.model.GetPostResponse
import com.ddd.pollpoll.core.network.model.PostPostRequest
import com.ddd.pollpoll.core.network.model.PostResponse
import com.ddd.pollpoll.core.network.model.PutVoteRequest
import com.ddd.pollpoll.core.network.retrofit.PollAPI
import javax.inject.Inject

interface PostRemoteSource {

    suspend fun insertPost(post: PostPostRequest)
    suspend fun putPoll(pollId: Int, pollItemIds: PutVoteRequest)
    suspend fun getPosts(lastPostId: Int): GetPostResponse
    suspend fun getPost(postId: Int): PostResponse
}

class PostRemoteSourceImp @Inject constructor(
    private val pollAPI: PollAPI
) : PostRemoteSource {
    override suspend fun insertPost(post: PostPostRequest): Unit = pollAPI.postPosts(post).executeHandle()
    override suspend fun putPoll(pollId: Int, pollItemIds: PutVoteRequest): Unit = pollAPI.putPoll(pollId, pollItemIds).executeHandle()
    override suspend fun getPosts(lastPostId: Int): GetPostResponse = pollAPI.getPosts(lastPostId).executeHandle()
    override suspend fun getPost(postId: Int): PostResponse = pollAPI.getPost(postId).executeHandle()
}
