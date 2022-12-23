package com.ddd.pollpoll.designsystem.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ddd.pollpoll.designsystem.theme.PollPollTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PollTopBar() {
    CenterAlignedTopAppBar(
        title = { Text(text = "투표 작성") },
        navigationIcon = {
            Icon(imageVector = Icons.Filled.AccountBox, contentDescription = null)
        },
        actions = {
            Icon(imageVector = Icons.Filled.Add, contentDescription = null)
        }
    )
}

@Preview
@Composable
fun TopAppBarPreview() {
    PollPollTheme() {
        PollTopBar()
    }
}
