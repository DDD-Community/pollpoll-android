package com.ddd.pollpoll.feature.vote.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ddd.pollpoll.feature.vote.InsertVoteRoute

  const val insertVoteRoute = "insertVote_route"

fun NavController.navigateToInsertVote() {
//    val encodedId = Uri.encode(topicId)
    this.navigate(insertVoteRoute)
}
fun NavGraphBuilder.insertVoteScreen(
    onBackClick: () -> Unit,
    onInsertButtonClick : () -> Unit
) {
    composable(
        route = insertVoteRoute,
        arguments = listOf(
//            navArgument(topicIdArg) { type = NavType.StringType }
        )
    ) {
        InsertVoteRoute(onCloseButtonClicked = onBackClick, onInsertButtonClicked = onInsertButtonClick)
    }
}
