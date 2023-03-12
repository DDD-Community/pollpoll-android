package com.ddd.pollpoll.core.network.remote

import com.ddd.pollpoll.core.network.handle.executeHandle
import com.ddd.pollpoll.core.network.model.*
import com.ddd.pollpoll.core.network.retrofit.PollAPI
import javax.inject.Inject

interface NickNameRemoteSource {

    suspend fun getNickNameRecommend(): GetNicknameRecommendResponse

    suspend fun putNickname(nicknameRequest: PutNicknameRequest)

    suspend fun getHasNickname() : GetHasNicknameResponse
}

class NickNameRemoteSourceImp @Inject constructor(
    private val pollAPI: PollAPI
) : NickNameRemoteSource {
    override suspend fun getNickNameRecommend(): GetNicknameRecommendResponse = pollAPI.getNickNameRecommend().executeHandle()
    override suspend fun putNickname(nicknameRequest: PutNicknameRequest)  = pollAPI.putNickname(nicknameRequest).executeHandle()
    override suspend fun getHasNickname(): GetHasNicknameResponse = pollAPI.getHasNickname().executeHandle()

}
