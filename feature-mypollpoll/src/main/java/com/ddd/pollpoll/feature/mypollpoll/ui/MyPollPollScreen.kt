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

import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.pollpoll.MyPagePollType
import com.ddd.pollpoll.core.ui.PollCardLazyList
import com.ddd.pollpoll.designsystem.component.PollButton
import com.ddd.pollpoll.designsystem.component.PollOutLineTextField
import com.ddd.pollpoll.designsystem.component.PollTopBar
import com.ddd.pollpoll.designsystem.core.bottomseat.PollModalBottomSheetLayout
import com.ddd.pollpoll.designsystem.icon.PollIcon
import com.ddd.pollpoll.designsystem.icon.PollIcon.Cloud
import com.ddd.pollpoll.designsystem.icon.PollIcon.Fire
import com.ddd.pollpoll.designsystem.icon.PollIcon.Writing
import com.ddd.pollpoll.designsystem.theme.PollPollTheme
import com.ddd.pollpoll.feature.main.model.PostUi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MyPollPollRoute(
    modifier: Modifier = Modifier,
    navigateToSettings: () -> Unit,
    navigateToReadVote: (Int) -> Unit,
    viewModel: MyPollPollViewModel = hiltViewModel(),
) {
    val posts = viewModel.posts
    val uiState = viewModel.uiState.collectAsState().value
    val lazyColumnListState = rememberLazyListState()
    var isNickNameDialogShow by remember { mutableStateOf(false) }
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Hidden,
        skipHiddenState = false,
    )
    var nickNameState by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val shouldStartPaginate = remember {
        derivedStateOf {
            viewModel.canPaginate && (
                // 현재 보이는것보다
                lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                    ?: -9
                ) >= (lazyColumnListState.layoutInfo.totalItemsCount - 1)
        }
    }
    if (isNickNameDialogShow) {
        PollModalBottomSheetLayout(sheetState = sheetState, onDismissRequest = {
            isNickNameDialogShow = false
        }) {
            MyPageBottomSheet(
                text = nickNameState,
                onTextChange = { nickNameState = it },
                onCloseButtonClicked = {
                    coroutineScope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        isNickNameDialogShow = false
                    }
                },
            )
        }
    }

    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (shouldStartPaginate.value && viewModel.listState == MyPollPollViewModel.ListState.IDLE) {
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
        nickNameModifyClicked = {
            coroutineScope.launch {
                isNickNameDialogShow = true
                delay(500L)
                sheetState.expand()
            }
        },
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
    nickNameModifyClicked: () -> Unit = {},
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
                            nickNameModifyClicked = nickNameModifyClicked,
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
fun PollPollTab(
    uiState: MyPollPollUiState,
    myPollClicked: () -> Unit,
    participatePollClicked: () -> Unit,
    watchPollClicked: () -> Unit,
) {
    Layout(
        content = {
            PollRecord(
                Writing,
                "내가 쓴 투표",
                uiState.myPagePollType == MyPagePollType.MY_POLL,
                uiState.myPollCount,
                myPollClicked,
            )
            PollRecord(
                Fire,
                "참여한 투표",
                uiState.myPagePollType == MyPagePollType.PARTICIPATE_POLL,
                uiState.participateCount,
                participatePollClicked,
            )
            PollRecord(
                Cloud,
                "구경한 투표",
                uiState.myPagePollType == MyPagePollType.WATCH_POLL,
                uiState.watchPollCount,
                watchPollClicked,
            )
            Image(
                imageVector = ImageVector.vectorResource(id = PollIcon.MyPollPollTriangle),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.background(PollPollTheme.colors.gray_300))
        },
        modifier = Modifier.clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)),
        measurePolicy = { measurables, constraints ->
            val pollMeasurable = measurables[0]?.also {
                it.layoutId == "MY_POLL"
            }?.measure(constraints.asLoose()) ?: throw NullPointerException(
                "pollMeasurable is null",
            )
            val participateMeasurable = measurables[1].also {
                it.layoutId == "PARTICIPATE_POLL"
            }?.measure(constraints.asLoose()) ?: throw NullPointerException(
                "participateMeasurable is null",
            )
            val watchMeasurable = measurables[2].also {
                it.layoutId == "WATCH_POLL"
            }?.measure(constraints.asLoose()) ?: throw NullPointerException(
                "watchMeasurable is null",
            )

            val cursorMeasurable = measurables[3].also {
                it.layoutId == "CURSOR"
            }?.measure(constraints.asLoose()) ?: throw NullPointerException(
                "cursorMeasurable is null",
            )
            val spaceMeasurable = measurables[4].also {
                it.layoutId == "SPACE"
            }?.measure(Constraints.fixed(constraints.maxWidth , cursorMeasurable.height)) ?: throw NullPointerException(
                "spaceMeasurable is null",
            )

            val width = pollMeasurable.width + participateMeasurable.width + watchMeasurable.width
            val spacer = (constraints.maxWidth - width) / 2

            // Poll 크기의 절반하고도 남은 값
            val cursorHalf = (pollMeasurable.width - cursorMeasurable.width) / 2
            layout(
                width = constraints.maxWidth,
                height = pollMeasurable.height + cursorMeasurable.height,
            ) {
                pollMeasurable.placeRelative(0, 0)
                participateMeasurable.placeRelative(
                    pollMeasurable.width + spacer,
                    0,
                )
                watchMeasurable.placeRelative(
                    participateMeasurable.width + pollMeasurable.width + spacer * 2,
                    0,
                )
                spaceMeasurable.placeRelative(
                    0,
                    pollMeasurable.height,
                )
                cursorMeasurable.place(
                    cursorHalf,
                    pollMeasurable.height - 10,
                )

            }
        },
    )
//    Row(
//        modifier = Modifier
//            .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)),
//    ) {
//        PollRecord(
//            Writing,
//            "내가 쓴 투표",
//            uiState.myPagePollType == MyPagePollType.MY_POLL,
//            uiState.myPollCount,
//            myPollClicked,
//        )
//        Spacer(Modifier.weight(1f))
//        PollRecord(
//            Fire,
//            "참여한 투표",
//            uiState.myPagePollType == MyPagePollType.PARTICIPATE_POLL,
//            uiState.participateCount,
//            participatePollClicked,
//        )
//        Spacer(Modifier.weight(1f))
//        PollRecord(
//            Cloud,
//            "구경한 투표",
//            uiState.myPagePollType == MyPagePollType.WATCH_POLL,
//            uiState.watchPollCount,
//            watchPollClicked,
//        )
//    }
//    Spacer(modifier = Modifier.height(20.dp))
//    AnimatedContent(
//        targetState = uiState.myPagePollType,
//        label = "",
//        transitionSpec = {
//            slideIntoContainer(
//                animationSpec = tween(),
//                towards = if (this.initialState.ordinal < this.targetState.ordinal) {
//                    AnimatedContentTransitionScope.SlideDirection.Right
//                } else {
//                    AnimatedContentTransitionScope.SlideDirection.Left
//                },
//            ) togetherWith
//                ExitTransition.None
//        },
//    ) { myPagePollType ->
//        Row(
//            modifier = Modifier
//                .offset(y = (-10).dp)
//                .padding(horizontal = 45.dp),
//        ) {
//            if (myPagePollType != MyPagePollType.MY_POLL) {
//                Spacer(
//                    modifier = Modifier.weight(
//                        1f,
//                    ),
//                )
//            }
//
//            if (myPagePollType != MyPagePollType.WATCH_POLL) {
//                Spacer(modifier = Modifier.weight(1f))
//            }
//        }
//    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MyPollPollHeader(
    uiState: MyPollPollUiState,
    myPollClicked: () -> Unit,
    participatePollClicked: () -> Unit,
    watchPollClicked: () -> Unit,
    nickNameModifyClicked: () -> Unit = {},
) {
    Column {
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .padding(horizontal = 20.dp),
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,

            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = PollPollTheme.typography.heading04.toSpanStyle().copy(
                                color = PollPollTheme.colors.gray_900,
                            ),
                        ) {
                            append(uiState.nickName)
                        }
                        withStyle(
                            style = PollPollTheme.typography.body01.toSpanStyle().copy(
                                color = PollPollTheme.colors.gray_500,
                            ),
                        ) {
                            append(" 님,\n" + "쌓인 고민거리들 같이 해결해요!")
                        }
                    },
                )
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PollPollTheme.colors.gray_050,
                    ),
                    border = BorderStroke(1.dp, PollPollTheme.colors.gray_200),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 3.5.dp),
                    onClick = nickNameModifyClicked,
                ) {
                    Text(
                        text = "닉네임수정",
                        style = PollPollTheme.typography.body04,
                        color = PollPollTheme.colors.gray_700,
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            PollPollTab(uiState, myPollClicked, participatePollClicked, watchPollClicked)
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
            Text(
                text = title,
                style = PollPollTheme.typography.body02,
                color = contentColor,
            )
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
fun MyPageBottomSheet(
    text: String = "",
    onTextChange: (String) -> Unit = {},
    onCloseButtonClicked: () -> Unit = {},
) {
    val isError = text.length > 15 || !text.matches(Regex("^[^!@#\$%^&*(),.?\":{}|<>]*\$"))
    val errorText = if (text.isEmpty()) {
        "15자이내/특수문자 입력 불가"
    } else if (isError) {
        "사용할 수 없는 닉네임입니다"
    } else {
        ""
    }

    Column {
        Spacer(modifier = Modifier.height(30.dp))
        IconButton(
            modifier = Modifier.align(Alignment.End),
            onClick = onCloseButtonClicked,
        ) {
            Icon(
                painter = painterResource(id = PollIcon.Close),
                contentDescription = "닉네임 수정 닫기",
            )
        }
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = PollPollTheme.typography.heading04,
            text = "글을 작성할 때 사용할\n 내 닉네임을 입력해주세요",
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(20.dp))
        PollOutLineTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            value = text,
            onValueChange = onTextChange,
            placeHolderText = "변경할 닉네임을 입력해주세요",
            trailingIcon = {},
            textFieldColors = TextFieldDefaults.colors(
                focusedContainerColor = PollPollTheme.colors.gray_050,
                unfocusedContainerColor = PollPollTheme.colors.gray_050,
                disabledContainerColor = PollPollTheme.colors.gray_050,
            ),
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            modifier = Modifier.padding(start = 49.dp),
            style = PollPollTheme.typography.body04,
            text = errorText,
            color = if (isError) PollPollTheme.colors.accent else PollPollTheme.colors.gray_400,
        )
        Spacer(modifier = Modifier.height(40.dp))
        PollButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp),
            shape = RoundedCornerShape(20.dp),
        ) {
            Text(text = "입력 완료")
        }
        Spacer(modifier = Modifier.height(60.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun PollRecordPreview() {
    PollPollTheme {
        MypollpollScreen(
            navigateToSettings = {},
            navigateToReadVote = {},
            posts = emptyList(),
            uiState = MyPollPollUiState("멋쟁이 다람쥐 102", MyPagePollType.MY_POLL, 0, 0, 0),
            myPollClicked = {},
            participatePollClicked = {},
            watchPollClicked = {},
            lazyColumnListState = rememberLazyListState(),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyPageBottomSheetPreview() {
    MyPageBottomSheet()
}

data class TabPosition(
    val left: Dp,
    val width: Dp,
)

enum class PollTabID {
    PRE_CALCULATE_ITEM,
    ITEM,
    INDICATOR,
}

fun Constraints.asLoose(
    height: Boolean = true,
    width: Boolean = false,
) = copy(
    minHeight = if (height) 0 else minHeight,
    minWidth = if (width) 0 else minWidth,
)
