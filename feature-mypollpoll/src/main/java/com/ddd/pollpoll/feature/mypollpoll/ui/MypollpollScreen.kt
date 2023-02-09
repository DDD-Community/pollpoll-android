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

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.pollpoll.core.network.model.GetPostResponse
import com.ddd.pollpoll.core.network.model.PostResponse
import com.ddd.pollpoll.designsystem.component.PollLabel
import com.ddd.pollpoll.designsystem.component.PollProgressBar
import com.ddd.pollpoll.designsystem.component.PollTopBar
import com.ddd.pollpoll.designsystem.icon.PollIcon
import com.ddd.pollpoll.designsystem.icon.PollIcon.Cloud
import com.ddd.pollpoll.designsystem.icon.PollIcon.Fire
import com.ddd.pollpoll.designsystem.icon.PollIcon.Writing
import com.ddd.pollpoll.designsystem.theme.PollPollTheme
import java.text.SimpleDateFormat
import java.util.*

@Composable
internal fun MyPollPollRoute(
    modifier: Modifier = Modifier,
    viewModel: MypollpollViewModel = hiltViewModel()
) {
    val posts = viewModel.posts.collectAsState().value
    MypollpollScreen(
        modifier = modifier,
        posts
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MypollpollScreen(
    modifier: Modifier = Modifier,
    posts: GetPostResponse?
) {
    Scaffold(topBar = {
        Column() {
            PollTopBar(
                title = {
                    Text(text = "마이 페이지")
                },
                navigationIconColor = Color.Black,
                titleContentColor = PollPollTheme.colors.gray_900,
                actionIconColor = Color.Black,

                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = PollIcon.LeftArrow),
                            contentDescription = ""
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = PollIcon.Close),
                            contentDescription = ""
                        )
                    }
                }
            )
        }
    }) {
        Surface(modifier = Modifier.padding(it)) {
            Column(
                modifier = Modifier
                    .background(color = PollPollTheme.colors.gray_050)
            ) {
                MyPollPollHeader()
                MyPollPollBody(posts)
            }
        }
    }
}

@Composable
fun MyPollPollHeader() {
    Column(modifier = Modifier) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                .background(color = Color.White)
                .padding(horizontal = 20.dp, vertical = 15.dp)
        ) {
            PollRecord(Writing, "내가 쓴 투표", true)
            Spacer(Modifier.weight(1f))
            PollRecord(Fire, "참여한 투표")
            Spacer(Modifier.weight(1f))
            PollRecord(Cloud, "구경한 투표")
        }
        Image(imageVector = ImageVector.vectorResource(id = PollIcon.MyPollPollTriangle), contentDescription = null, modifier = Modifier.offset(x = 40.dp, y = (-10).dp))
    }
}

@Composable
fun PollRecord(
    iconRes: Int,
    title: String,
    selected: Boolean = false,
    onClick: () -> Unit = {}

) {
    Box(
        modifier = Modifier
            .clip(
                RoundedCornerShape(12.dp)
            )
            .height(114.dp)
            .width(100.dp)
    ) {
        val contentColor = if (selected) PollPollTheme.colors.primary_500 else PollPollTheme.colors.gray_400
        val bgColor = if (selected) PollPollTheme.colors.primary_050 else PollPollTheme.colors.gray_050
        val circleColor = if (selected) PollPollTheme.colors.primary_500 else PollPollTheme.colors.gray_400
        val iconColor = if (selected) PollPollTheme.colors.primary_050 else PollPollTheme.colors.gray_400

        Column(
            modifier = Modifier
                .padding(top = 20.dp)
                .clip(RoundedCornerShape(12.dp))
                .height(94.dp)
                .width(100.dp)
                .background(color = bgColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.size(32.dp))
            Text(text = title, style = PollPollTheme.typography.body02, color = contentColor)
            Spacer(Modifier.size(5.dp))
            Row() {
                Text(
                    text = "0",
                    style = PollPollTheme.typography.heading05,
                    modifier = Modifier.alignByBaseline()
                )
                Spacer(Modifier.size(3.dp))
                Text(
                    text = "개",
                    style = PollPollTheme.typography.body04,
                    color = PollPollTheme.colors.gray_500,
                    modifier = Modifier.alignByBaseline()
                )
            }
        }

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp)
                .background(color = contentColor)
                .align(Alignment.TopCenter)
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun MyPollPollBody(posts: GetPostResponse?) {
    if (posts != null) {
        for (post in posts.posts) {
            PollCard(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                post = post
            )
        }
    }
}

@Composable
fun PollCard(
    modifier: Modifier = Modifier,
    expireDate: Date = Date(),
    participantsCount: Int = 0,
    onClick: () -> Unit = {},
    post: PostResponse
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                PollLabel("초이스")
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "${post.categoryName}",
                    color = PollPollTheme.colors.gray_900,
                    style = PollPollTheme.typography.body04
                )
            }
            Spacer(modifier = Modifier.size(15.dp))

            Text(
                text = "${post.title}",
                color = PollPollTheme.colors.gray_900,
                style = PollPollTheme.typography.heading05
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "${post.contents}",
                color = PollPollTheme.colors.gray_700,
                style = PollPollTheme.typography.heading05,
                fontWeight = FontWeight.Normal,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.size(25.dp))

            Row() {
                Icon(
                    painter = painterResource(id = PollIcon.Alarm),
                    contentDescription = "",
                    tint = Color(0xff8477F8)
                )
                Spacer(modifier = Modifier.size(3.dp))
                TimeText(expireDate = expireDate)
            }
            Spacer(modifier = Modifier.size(8.dp))

            PollProgressBar()

            Spacer(modifier = Modifier.size(32.dp))

            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = PollPollTheme.colors.gray_050)
            ) {
                Text(text = "지금 ", color = PollPollTheme.colors.gray_700, style = PollPollTheme.typography.body03)
                Text(text = "${post.participantCount}명", color = PollPollTheme.colors.primary_500, style = PollPollTheme.typography.body03)
                Text(text = " 참여 중이에요", color = PollPollTheme.colors.gray_700, style = PollPollTheme.typography.body03)
            }
        }
    }
}

@Composable
fun TimeText(expireDate: Date) {
    // date gap millisceond to date

    val expireDateTime = expireDate.time
    val currentTime = Date().time
    val diff = currentTime - expireDateTime
    val sdf = SimpleDateFormat("MM.dd HH:mm")

    val diffSeconds: Long = diff / 1000
    val diffMinutes: Long = diff / (60 * 1000)
    val diffHours: Long = diff / (60 * 60 * 1000)
    val diffDays: Long = diff / (24 * 60 * 60 * 1000)

    val hoursString = if (diffHours < 10) "0$diffHours" else "$diffHours"
    val minuteString = if (diffMinutes < 10) "0$diffMinutes" else diffMinutes
    val secondsString = if (diffSeconds < 10) "0$diffSeconds" else diffSeconds

    Text(
        text = "${diffDays}일 $hoursString:$minuteString:$secondsString 남았어요!",
        color = PollPollTheme.colors.gray_400,
        style = PollPollTheme.typography.body03
    )
}

@Composable
fun HitsText(hits: Int) {
    Text(
        text = "조회수 $hits",
        color = PollPollTheme.colors.gray_400,
        style = PollPollTheme.typography.body03
    )
}

// Previews

@Preview(showBackground = true)
@Composable
private fun PollRecordPreview() {
    PollPollTheme {
        MypollpollScreen(posts = null)
    }
}
