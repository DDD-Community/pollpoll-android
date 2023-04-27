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
 */

package com.ddd.pollpoll.feature.mypollpoll.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.pollpoll.MyPagePollType
import com.ddd.pollpoll.core.ui.PollCardLazyList
import com.ddd.pollpoll.designsystem.component.PollTopBar
import com.ddd.pollpoll.designsystem.icon.PollIcon
import com.ddd.pollpoll.designsystem.icon.PollIcon.Cloud
import com.ddd.pollpoll.designsystem.icon.PollIcon.Fire
import com.ddd.pollpoll.designsystem.icon.PollIcon.Writing
import com.ddd.pollpoll.designsystem.theme.PollPollTheme
import com.ddd.pollpoll.feature.main.model.PostUi
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

@Composable
internal fun MyPollPollRoute(
    modifier: Modifier = Modifier,
    navigateToSettings: () -> Unit,
    navigateToReadVote: (Int) -> Unit,
    viewModel: MypollpollViewModel = hiltViewModel(),
) {
    val posts = viewModel.posts
    val uiState = viewModel.uiState.collectAsState().value
    val lazyColumnListState = rememberLazyListState()
    val shouldStartPaginate = remember {
        derivedStateOf {
            viewModel.canPaginate && (
                // 현재 보이는것보다
                lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                    ?: -9
                ) >= (lazyColumnListState.layoutInfo.totalItemsCount - 1)
        }
    }

    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (shouldStartPaginate.value && viewModel.listState == MypollpollViewModel.ListState.IDLE) {
            viewModel.getPost()
        }
    }
    MypollpollScreen(
        modifier = modifier,
        navigateToSettings,
        navigateToReadVote,
        posts,
        uiState,
        { viewModel.changePollType(MyPagePollType.MY_POLL) },
        { viewModel.changePollType(MyPagePollType.PARTICIPATE_POLL) },
        { viewModel.changePollType(MyPagePollType.WATCH_POLL) },
        lazyColumnListState = lazyColumnListState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MypollpollScreen(
    modifier: Modifier = Modifier,
    navigateToSettings: () -> Unit,
    navigateToReadVote: (Int) -> Unit,
    posts: List<PostUi>,
    uiState: MyPollPollUiState,
    myPollClicked: () -> Unit,
    participatePollClicked: () -> Unit,
    watchPollClicked: () -> Unit,
    lazyColumnListState: LazyListState,
) {
    Scaffold(topBar = {
        Column(Modifier.background(Color.White)) {
            PollTopBar(
                title = {
                    Text(text = "마이 페이지")
                },
                navigationIconColor = Color.Black,
                titleContentColor = PollPollTheme.colors.gray_900,
                actionIconColor = Color.Black,

                navigationIcon = {
                },
                actions = {
                    IconButton(onClick = { navigateToSettings() }) {
                        Icon(
                            painter = painterResource(id = PollIcon.Settings),
                            contentDescription = "",
                        )
                    }
                },
            )
        }
    }) {
        Surface(modifier = Modifier.padding(it)) {
            Column(
                modifier = Modifier
                    .background(color = PollPollTheme.colors.gray_050)
                    .fillMaxSize(),
            ) {
                LazyColumn(
                    Modifier
                        .fillMaxSize(),
                    state = lazyColumnListState,
                ) {
                    item {
                        MyPollPollHeader(
                            uiState,
                            myPollClicked,
                            participatePollClicked,
                            watchPollClicked,
                        )
                    }
                    item {
                        Row(Modifier.padding(top = 5.dp, bottom = 12.dp, end = 10.dp)) {
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                painter = painterResource(id = PollIcon.CheckCircle),
                                contentDescription = "",
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "진행중",
                                color = PollPollTheme.colors.gray_500,
                                style = PollPollTheme.typography.body03,
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                    PollCardLazyList(posts, navigateToReadVote)
                }
            }
        }
    }
}

@Composable
fun MyPollPollHeader(
    uiState: MyPollPollUiState,
    myPollClicked: () -> Unit,
    participatePollClicked: () -> Unit,
    watchPollClicked: () -> Unit,
) {
    Column(modifier = Modifier) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                .background(color = Color.White)
                .padding(horizontal = 20.dp, vertical = 15.dp),
        ) {
            PollRecord(
                Writing,
                "내가 쓴 투표",
                uiState.myPagePollType == MyPagePollType.MY_POLL,
                uiState.myPollCount,
                myPollClicked,
            )
            Spacer(Modifier.weight(1f))
            PollRecord(
                Fire,
                "참여한 투표",
                uiState.myPagePollType == MyPagePollType.PARTICIPATE_POLL,
                uiState.participateCount,
                participatePollClicked,
            )
            Spacer(Modifier.weight(1f))
            PollRecord(
                Cloud,
                "구경한 투표",
                uiState.myPagePollType == MyPagePollType.WATCH_POLL,
                uiState.watchPollCount,
                watchPollClicked,
            )
        }

        Row(
            modifier = Modifier
                .offset(y = (-10).dp)
                .padding(horizontal = 45.dp),
        ) {
            if (uiState.myPagePollType != MyPagePollType.MY_POLL) {
                Spacer(
                    modifier = Modifier.weight(
                        1f,
                    ),
                )
            }
            Image(
                imageVector = ImageVector.vectorResource(id = PollIcon.MyPollPollTriangle),
                contentDescription = null,
            )
            if (uiState.myPagePollType != MyPagePollType.WATCH_POLL) {
                Spacer(
                    modifier = Modifier.weight(
                        1f,
                    ),
                )
            }
        }
    }
}

@Composable
fun PollRecord(
    iconRes: Int,
    title: String,
    selected: Boolean = false,
    count: Int = 0,
    onClick: () -> Unit = {},

) {
    Box(
        modifier = Modifier
            .clip(
                RoundedCornerShape(12.dp),
            )
            .height(114.dp)
            .width(100.dp),
    ) {
        val contentColor =
            if (selected) PollPollTheme.colors.primary_500 else PollPollTheme.colors.gray_400
        val bgColor =
            if (selected) PollPollTheme.colors.primary_050 else PollPollTheme.colors.gray_050
        val circleColor =
            if (selected) PollPollTheme.colors.primary_500 else PollPollTheme.colors.gray_400
        val iconColor =
            if (selected) PollPollTheme.colors.primary_050 else PollPollTheme.colors.gray_400

        Column(
            modifier = Modifier
                .padding(top = 20.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable {
                    onClick()
                }
                .height(94.dp)
                .width(100.dp)
                .background(color = bgColor),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.size(32.dp))
            Text(text = title, style = PollPollTheme.typography.body02, color = contentColor)
            Spacer(Modifier.size(5.dp))
            Row() {
                Text(
                    text = count.toString(),
                    style = PollPollTheme.typography.heading05,
                    modifier = Modifier.alignByBaseline(),
                )
                Spacer(Modifier.size(3.dp))
                Text(
                    text = "개",
                    style = PollPollTheme.typography.body04,
                    color = PollPollTheme.colors.gray_500,
                    modifier = Modifier.alignByBaseline(),
                )
            }
        }

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp)
                .background(color = contentColor)
                .align(Alignment.TopCenter),
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center),
            )
        }
    }
}


@Composable
fun TimeText(expireDateTime: Long, currentTime: Long) {
    val diff = expireDateTime - currentTime
    val diffAbsoluteValue = diff.absoluteValue
    val sdf = SimpleDateFormat("MM.dd HH:mm")

    val diffSeconds: Long = (diffAbsoluteValue / 1000) % 60
    val diffMinutes: Long = (diffAbsoluteValue / (60 * 1000)) % 60
    val diffHours: Long = (diffAbsoluteValue / (60 * 60 * 1000)) % 24
    val diffDays: Long = diffAbsoluteValue / (24 * 60 * 60 * 1000)

    val hoursString = if (diffHours < 10) "0$diffHours" else "$diffHours"
    val minuteString = if (diffMinutes < 10) "0$diffMinutes" else diffMinutes
    val secondsString = if (diffSeconds < 10) "0$diffSeconds" else diffSeconds

    if (diff < 0) {
        Text(
            text = "종료된 투표",
            color = PollPollTheme.colors.gray_300,
            style = PollPollTheme.typography.body03,
        )
    } else {
        Text(
            text = "${diffDays}일 $hoursString:$minuteString:$secondsString 남았어요!",
            color = PollPollTheme.colors.gray_400,
            style = PollPollTheme.typography.body03,
        )
    }
}

@Composable
fun HitsText(hits: Int) {
    Text(
        text = "조회수 $hits",
        color = PollPollTheme.colors.gray_400,
        style = PollPollTheme.typography.body03,
    )
}

// Previews

// modifier: Modifier = Modifier,
// navigateToSettings: () -> Unit,
// posts: GetPostResponse?,
// uiState: MyPollPollUiState,
// myPollClicked: () -> Unit,
// participatePollClicked: () -> Unit,
// watchPollClicked: () -> Unit,

@Preview(showBackground = true)
@Composable
private fun PollRecordPreview() {
    PollPollTheme {
        MypollpollScreen(
            navigateToSettings = {},
            navigateToReadVote = {},
            posts = emptyList(),
            uiState = MyPollPollUiState(MyPagePollType.MY_POLL, 0, 0, 0),
            myPollClicked = {},
            participatePollClicked = {},
            watchPollClicked = {},
            lazyColumnListState = rememberLazyListState(),
        )
    }
}
