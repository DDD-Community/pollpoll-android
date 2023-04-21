package com.ddd.pollpoll.feature.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ddd.pollpoll.feature.main.MainScreenRoute

// internal const val topicIdArg = "topicId"

 const val mainRoute = "main_route"

fun NavController.navigateToMain() {
//    val encodedId = Uri.encode(topicId)
    this.navigate(mainRoute)
}

fun NavGraphBuilder.MainScreen(
    navigateToReadVote: (Int) -> Unit,
    navigateToSearch: () -> Unit,
) {
    composable(
        route = mainRoute,
        arguments = listOf(),
    ) {
        MainScreenRoute(
            navigateToReadVote = navigateToReadVote,
            navigateToSearch = navigateToSearch,
        )
    }
}
