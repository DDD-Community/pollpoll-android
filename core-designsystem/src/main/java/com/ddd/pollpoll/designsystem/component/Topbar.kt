package com.ddd.pollpoll.designsystem.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ddd.pollpoll.core.ui.topbar.CustomTopBar
import com.ddd.pollpoll.designsystem.component.PollTopBarDefaults.TopBarBaseHeight
import com.ddd.pollpoll.designsystem.theme.PollPollTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PollTopBar(
    title: @Composable () -> Unit = {},
    style: TextStyle = PollPollTheme.typography.heading05,
    navigationIconColor: Color,
    titleContentColor: Color,
    actionIconColor: Color,
    height: Dp = TopBarBaseHeight,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable () -> Unit = {}
) {
    val context = LocalContext.current

    CustomTopBar(
        navigationIconContentColor = navigationIconColor,
        titleContentColor = titleContentColor,
        actionIconContentColor = actionIconColor,
        titleTextStyle = style,
        title = title,
        heightPx = with(Density(context)) { height.toPx() },
        navigationIcon = navigationIcon,
        actions = actions
    )
}

object PollTopBarDefaults {
    val TopBarBaseHeight = 88.dp
}