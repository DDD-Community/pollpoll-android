/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ // ktlint-disable filename

package com.ddd.pollpoll.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ddd.pollpoll.designsystem.icon.PollIcon
import com.ddd.pollpoll.designsystem.theme.PollPollTheme
import com.ddd.pollpoll.feature.login.ui.navigation.loginRoute
import com.ddd.pollpoll.feature.login.ui.navigation.loginScreen
import com.ddd.pollpoll.feature.login.ui.navigation.navigateToNickNameScreen
import com.ddd.pollpoll.feature.login.ui.navigation.nickNameRoute
import com.ddd.pollpoll.feature.login.ui.navigation.nickNameScreen
import com.ddd.pollpoll.feature.mypollpoll.navigation.myPollPollRoute
import com.ddd.pollpoll.feature.mypollpoll.navigation.myPollPollScreen
import com.ddd.pollpoll.feature.settings.ui.navigation.navigateToSettings
import com.ddd.pollpoll.feature.settings.ui.navigation.settingsScreen
import com.ddd.pollpoll.feature.vote.navigation.*
import com.ddd.pollpoll.feature.vote.navigation.mainRoute

val bottomInvisibleList = listOf(
    loginRoute,
    insertVoteRoute,
    nickNameRoute,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    Scaffold(
        bottomBar = {
            BottomNavigation(navController, currentBackStack)
        },
        floatingActionButton = {
            if (currentBackStack?.destination?.route == mainRoute) {
                FloatingActionButton(
                    shape = CircleShape,
                    containerColor = PollPollTheme.colors.gray_700,
                    onClick = {
                        navController.navigateToInsertVote()
                    },
                ) {
                    Icon(painter = painterResource(id = PollIcon.Insert), "", tint = PollPollTheme.colors.gray_050)
                }
            }
        },
    ) { paddingValues ->
        NavHost(modifier = Modifier.padding(paddingValues), navController = navController, startDestination = "login_route") {
            loginScreen(
                navigateToMainScreen = { navController.navigateToMain() },
                navigateToNickNameScreen = { navController.navigateToNickNameScreen() },
            )
            nickNameScreen(
                navigateToMainScreen = { navController.navigateToMain() },
            )
            insertVoteScreen(
                onBackClick = { navController.popBackStack() },
                navToMain = { navController.navigateToMain() },
            )
            readVoteScreen(
                onBackClick = { navController.popBackStack() },
            )
            myPollPollScreen (
                navigateToSettings = { navController.navigateToSettings()},
                navigateToReadVote = { postId ->
                    navController.navigateToReadVote(postId)
                }
            )
            settingsScreen(
                navigateUp = { navController.navigateUp() }
            )
            MainScreen(
                navigateToReadVote = { postId ->
                    navController.navigateToReadVote(postId)
                }
            )
        }
    }
}

@Composable
fun BottomNavigation(navController: NavHostController, currentBackStack: NavBackStackEntry?) {
    val currentRoute = currentBackStack?.destination?.route
    if (currentRoute in bottomInvisibleList) {
    } else {
        Column(Modifier.background(Color.White)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .shadow(
                        spotColor = Color(0x00000000),
                        elevation = 24.dp,
                    ),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                BottomNavBarItem(PollIcon.Home, "홈", mainRoute, currentRoute) {
                    navController.navigate(mainRoute) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }
                }
                BottomNavBarItem(PollIcon.MyPollPoll, "마이폴폴", myPollPollRoute, currentRoute) {
                    navController.navigate(myPollPollRoute) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavBarItem(
    resId: Int,
    itemName: String,
    route: String,
    currentRoute: String?,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .size(75.dp, 51.dp)
            .clip(CircleShape)
            .background(Color.White)
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val color =
            if (currentRoute?.contains(route) == true) PollPollTheme.colors.primary_500 else PollPollTheme.colors.gray_700

        Spacer(modifier = Modifier.size(8.dp))
        Icon(
            painter = painterResource(resId),
            contentDescription = itemName,
            modifier = Modifier.size(24.dp),
            tint = color,
        )
        Text(text = itemName, style = PollPollTheme.typography.desc, color = color)
    }
}
