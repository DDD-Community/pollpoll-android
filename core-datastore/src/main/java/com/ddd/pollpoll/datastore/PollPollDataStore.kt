package com.ddd.pollpoll.datastore

import kotlinx.coroutines.flow.Flow

interface PollPollDataStore {

    fun getToken(): Flow<String>

    suspend fun updateToken(name: String)

    fun getNickName() : Flow<String>

    suspend fun updateNickName(nickName : String)

    fun clear()
}
