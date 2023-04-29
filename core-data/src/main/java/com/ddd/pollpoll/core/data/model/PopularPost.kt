package com.ddd.pollpoll.core.data.model

import com.ddd.pollpoll.PopularPost
import com.ddd.pollpoll.core.network.model.GetPopularResponse

fun GetPopularResponse.asExternalModel() = PopularPost(
    mostParticipatePost = this.mostParticipatePost.asExternalModel(),
    mostWatchPost = this.mostWatchPost.asExternalModel(),
    endingSoonPost = this.endingSoonPost.asExternalModel(),
)
