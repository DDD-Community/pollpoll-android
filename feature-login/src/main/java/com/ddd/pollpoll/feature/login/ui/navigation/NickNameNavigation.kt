package com.ddd.pollpoll.feature.login.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ddd.pollpoll.feature.login.ui.NicknameRoute

const val nickNameRoute = "nickname_route"

fun NavController.navigateToNickNameScreen() {
//    val encodedId = Uri.encode(topicId)
    this.navigate(nickNameRoute)
}

fun NavGraphBuilder.nickNameScreen(
//    onBackClick: () -> Unit,
    navigateToMainScreen: () -> Unit,
) {
    composable(
        route = nickNameRoute,
        arguments = listOf(
//            navArgument(topicIdArg) { type = NavType.StringType }
        ),
    ) {
        NicknameRoute(
            /*onBackClick = onBackClick*/
            navigateToMainScreen = navigateToMainScreen,
        )
    }
}
