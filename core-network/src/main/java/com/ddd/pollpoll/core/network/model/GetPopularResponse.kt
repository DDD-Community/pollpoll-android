package com.ddd.pollpoll.core.network.model

import com.ddd.pollpoll.PopularPost

data class GetPopularResponse(
    val mostParticipatePost: PostResponse?,
    val mostWatchPost: PostResponse?,
    val endingSoonPost: PostResponse?
)

fun GetPopularResponse.asExternalModel() = PopularPost(
    mostParticipatePost = this.mostParticipatePost?.asExternalModel(),
    mostWatchPost = this.mostWatchPost?.asExternalModel(),
    endingSoonPost = this.endingSoonPost?.asExternalModel()
)
