package com.ddd.pollpoll.datastore

import kotlinx.coroutines.flow.Flow


interface PollPollDataStore {

    fun getToken() : Flow<String>

    suspend fun updateToken(name: String)


    fun clear()

}