package com.ddd.pollpoll.feature.vote.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ddd.pollpoll.feature.vote.ReadVoteRoute

// internal const val topicIdArg = "topicId"

fun NavController.navigateToReadVote(postId: Int) {
//    val encodedId = Uri.encode(topicId)
    this.navigate("readVote_route/$postId")
}
fun NavGraphBuilder.readVoteScreen(
    onBackClick: () -> Unit,
) {
    composable(
        route = "readVote_route/{postId}",
        arguments = listOf(
            navArgument("postId") {
                type = NavType.IntType
            },
        ),
    ) {
        val argsPostId = it.arguments?.getInt("postId") ?: 0
        ReadVoteRoute(onCloseButtonClicked = onBackClick, postId = argsPostId)
    }
}
