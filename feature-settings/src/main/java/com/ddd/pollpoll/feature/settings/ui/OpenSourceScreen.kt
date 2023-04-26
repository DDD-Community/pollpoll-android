package com.ddd.pollpoll.feature.settings.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mikepenz.aboutlibraries.ui.compose.LibrariesContainer

@Composable
fun OpenSourceScreenRoute(
    modifier: Modifier = Modifier,
) {
    OpenSourceScreen()
}

@Composable
fun OpenSourceScreen() {
    LibrariesContainer(
        modifier = Modifier.fillMaxSize(),
    )
}
