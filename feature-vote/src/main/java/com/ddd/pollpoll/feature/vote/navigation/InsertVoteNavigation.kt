package com.ddd.pollpoll.feature.vote.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ddd.pollpoll.feature.vote.InsertVoteRoute

// internal const val topicIdArg = "topicId"

fun NavGraphBuilder.insertVoteScreen(
    onBackClick: () -> Unit
) {
    composable(
        route = "insertVote_route",
        arguments = listOf(
//            navArgument(topicIdArg) { type = NavType.StringType }
        )
    ) {
        InsertVoteRoute(/*onBackClick = onBackClick*/)
    }
}
