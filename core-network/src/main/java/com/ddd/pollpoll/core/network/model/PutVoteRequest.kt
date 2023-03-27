package com.ddd.pollpoll.core.network.model

data class PutVoteRequest(
    val pollItemIds: List<Int>
)