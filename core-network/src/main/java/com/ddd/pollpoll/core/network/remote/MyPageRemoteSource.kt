package com.ddd.pollpoll.core.network.remote

import com.ddd.pollpoll.MyPageType
import com.ddd.pollpoll.core.network.handle.executeHandle
import com.ddd.pollpoll.core.network.model.GetHasNicknameResponse
import com.ddd.pollpoll.core.network.model.GetMyPageResponse
import com.ddd.pollpoll.core.network.model.GetNicknameRecommendResponse
import com.ddd.pollpoll.core.network.model.PutNicknameRequest
import com.ddd.pollpoll.core.network.retrofit.PollAPI
import retrofit2.http.Query
import javax.inject.Inject


interface MyPageRemoteSource {

    suspend fun getMyPage( lastPostId: Int, type: MyPageType): GetMyPageResponse

}
class MyPageRemoteSourceImp @Inject constructor(
    private val pollAPI: PollAPI
) : MyPageRemoteSource {
    override suspend fun getMyPage(lastPostId: Int, type: MyPageType): GetMyPageResponse  = pollAPI.getMyPage(lastPostId, type).executeHandle()
}