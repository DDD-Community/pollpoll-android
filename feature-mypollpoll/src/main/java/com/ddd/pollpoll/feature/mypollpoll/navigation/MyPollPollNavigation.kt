package com.ddd.pollpoll.feature.mypollpoll.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ddd.pollpoll.feature.mypollpoll.ui.MyPollPollRoute

// internal const val topicIdArg = "topicId"

const val myPollPollRoute = "my_poll_poll"

fun NavController.navigateToMyPollPoll() {
//    val encodedId = Uri.encode(topicId)
    this.navigate(myPollPollRoute)
}
fun NavGraphBuilder.myPollPollScreen(
    navigateToSettings: () -> Unit
) {
    composable(
        route = myPollPollRoute,
        arguments = listOf(
//            navArgument(topicIdArg) { type = NavType.StringType }
        )
    ) {
        MyPollPollRoute(navigateToSettings = navigateToSettings)
    }
}
