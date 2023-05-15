package com.ddd.pollpoll.feature.settings.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ddd.pollpoll.feature.settings.ui.OpenSourceScreenRoute

const val openSourceRoute = "opensource_route"

fun NavController.navigateToOpenSource() {
    this.navigate(openSourceRoute)
}

fun NavGraphBuilder.OpenSourceScreen(
) {
    composable(
        route = openSourceRoute,
    ) {
        OpenSourceScreenRoute()
    }
}
