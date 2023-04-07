package com.ddd.pollpoll.feature.vote.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ddd.pollpoll.ui.feature.SearchScreenRoute

internal const val searchRoute = "search_route"

fun NavController.navigateToSearch() {
//    val encodedId = Uri.encode(topicId)
    this.navigate(searchRoute)
}

fun NavGraphBuilder.SearchScreen(
    navigateToReadVote: (Int) -> Unit,
    backToMain: () -> Unit,
) {
    composable(
        route = searchRoute,
        arguments = listOf(),
    ) {
        SearchScreenRoute(navigateToReadVote = navigateToReadVote, backToMain = backToMain)
    }
}
