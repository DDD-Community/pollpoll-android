package com.ddd.pollpoll.feature.mypollpoll.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ddd.pollpoll.feature.mypollpoll.ui.MyPollPollRoute

// internal const val topicIdArg = "topicId"

fun NavController.navigateToMyPollPoll() {
//    val encodedId = Uri.encode(topicId)
    this.navigate("my_poll_poll")
}
fun NavGraphBuilder.myPollPollScreen(
    onBackClick: () -> Unit
) {
    composable(
        route = "my_poll_poll",
        arguments = listOf(
//            navArgument(topicIdArg) { type = NavType.StringType }
        )
    ) {
        MyPollPollRoute(/*onBackClick = onBackClick*/)
    }
}
