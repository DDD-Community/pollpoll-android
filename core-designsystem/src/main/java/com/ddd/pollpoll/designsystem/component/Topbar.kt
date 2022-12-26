package com.ddd.pollpoll.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddd.pollpoll.designsystem.theme.PollPollTheme
import com.example.core_designsystem.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PollTopBar(
    modifier: Modifier = Modifier,
    title: String,
    leftIcon: @Composable () -> Unit = {
        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_left_arrow), contentDescription = null)
    },
    rightIcon: @Composable RowScope.() -> Unit = {
        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_close), contentDescription = null)
    }

) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { Text(text = title) },
        navigationIcon = leftIcon,
        actions = rightIcon
    )
}

@Preview
@Composable
fun TopAppBarPreview() {
    PollPollTheme() {
        PollTopBar(title = "투표작성")
    }
}
