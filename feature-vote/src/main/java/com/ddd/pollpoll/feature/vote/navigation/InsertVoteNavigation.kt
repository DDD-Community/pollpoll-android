package com.ddd.pollpoll.feature.vote.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ddd.pollpoll.feature.vote.InsertVoteRoute

// internal const val topicIdArg = "topicId"

fun NavController.navigateToVote() {
//    val encodedId = Uri.encode(topicId)
    this.navigate("insertVote_route")
}
fun NavGraphBuilder.insertVoteScreen(
    onBackClick: () -> Unit
) {
    composable(
        route = "insertVote_route",
        arguments = listOf(
//            navArgument(topicIdArg) { type = NavType.StringType }
        )
    ) {
        InsertVoteRoute(onCloseButtonClicked = onBackClick)
    }
}
