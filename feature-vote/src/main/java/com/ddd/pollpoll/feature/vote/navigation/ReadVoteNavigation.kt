package com.ddd.pollpoll.feature.vote.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ddd.pollpoll.feature.vote.ReadVoteRoute

// internal const val topicIdArg = "topicId"

fun NavController.navigateToReadVote() {
//    val encodedId = Uri.encode(topicId)
    this.navigate("readVote_route")
}
fun NavGraphBuilder.readVoteScreen(
    onBackClick: () -> Unit
) {
    composable(
        route = "readVote_route",
        arguments = listOf(
//            navArgument(topicIdArg) { type = NavType.StringType }
        )
    ) {
        ReadVoteRoute(onCloseButtonClicked = onBackClick)
    }
}
