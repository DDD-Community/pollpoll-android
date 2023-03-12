package com.ddd.pollpoll.core.network.remote

import com.ddd.pollpoll.core.network.handle.executeHandle
import com.ddd.pollpoll.core.network.model.GetCategoriesResponse
import com.ddd.pollpoll.core.network.model.PostResponse
import com.ddd.pollpoll.core.network.retrofit.PollAPI
import javax.inject.Inject

interface CategoryRemoteSource {
    suspend fun getCategories(): GetCategoriesResponse
}

class CategoryRemoteSourceImp @Inject constructor(
    private val pollAPI: PollAPI
) : CategoryRemoteSource {
    override suspend fun getCategories(): GetCategoriesResponse = pollAPI.getCategories().executeHandle()

}
