package com.ddd.pollpoll.feature.vote.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ddd.pollpoll.ui.feature.MainScreenRoute

// internal const val topicIdArg = "topicId"

internal const val mainRoute = "main_route"

fun NavController.navigateToMain() {
//    val encodedId = Uri.encode(topicId)
    this.navigate(mainRoute)
}

fun NavGraphBuilder.MainScreen(
) {
    composable(
        route = mainRoute,
        arguments = listOf()
    ) {
        MainScreenRoute()
    }
}
