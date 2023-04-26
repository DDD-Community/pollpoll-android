package com.ddd.pollpoll.feature.settings.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ddd.pollpoll.feature.settings.ui.SettingsScreenRoute

const val settingsRoute = "settings_route"

fun NavController.navigateToSettings() {
//    val encodedId = Uri.encode(topicId)
    this.navigate(settingsRoute)
}

fun NavGraphBuilder.settingsScreen(
    navigateUp: () -> Unit,
    navigateToAboutLibraries: () -> Unit,
) {
    composable(
        route = settingsRoute,
        arguments = listOf(
//            navArgument(topicIdArg) { type = NavType.StringType }
        ),
    ) {
        SettingsScreenRoute(
            navigateUp = navigateUp,
            navigateToAboutLibraries = navigateToAboutLibraries,
        )
    }
}
