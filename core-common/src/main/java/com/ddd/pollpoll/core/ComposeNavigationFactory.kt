package com.ddd.pollpoll.core

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface ComposeNavigationFactory {
    fun create(navGraphBuilder: NavGraphBuilder, navController: NavController)
}
