package com.ddd.pollpoll.feature.login.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ddd.pollpoll.feature.login.ui.LoginRoute

const val loginRoute = "login_route"

fun NavGraphBuilder.loginScreen(
//    onBackClick: () -> Unit,
    navigateToMain: (String) -> Unit
) {
    composable(
        route = "login_route",
        arguments = listOf(
//            navArgument(topicIdArg) { type = NavType.StringType }
        )
    ) {
        LoginRoute(/*onBackClick = onBackClick*/ navigateToMain = navigateToMain)
    }
}
