package com.ddd.pollpoll.core.network.model

data class GetPopularResponse(
    val mostParticipatePost: PostResponse?,
    val mostWatchPost: PostResponse?,
    val endingSoonPost: PostResponse?
)

