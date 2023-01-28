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
import com.ddd.pollpoll.designsystem.icon.PollIcon
import com.ddd.pollpoll.designsystem.theme.PollPollTheme

@Composable
fun MypollpollScreen(modifier: Modifier = Modifier, viewModel: MypollpollViewModel = hiltViewModel()) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MypollpollScreen(

) {
    Column(
        modifier = Modifier
            .background(color = PollPollTheme.colors.gray_050)
    ) {
        MyPollPollHeader()
    }
}

@Composable
fun MyPollPollHeader() {
    Column(modifier = Modifier) {
        Row(modifier = Modifier.clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)).background(color = Color.White).padding(horizontal = 20.dp, vertical = 15.dp)) {
            PollRecord(true)
            Spacer(Modifier.size(10.dp))
            PollRecord()
            Spacer(Modifier.size(10.dp))
            PollRecord()
        }
        Image(imageVector = ImageVector.vectorResource(id = PollIcon.MyPollPollTriangle), contentDescription = null, modifier = Modifier.offset(x = 40.dp, y = (-10).dp))

    }
}

@Composable
fun PollRecord(selected: Boolean = false, onClick: () -> Unit = {}) {
    Box(modifier = Modifier
        .clip(
            RoundedCornerShape(12.dp)
        )
        .height(114.dp)
        .width(100.dp)) {

        val contentColor = if (selected) PollPollTheme.colors.primary_500 else PollPollTheme.colors.gray_400
        val bgColor = if (selected) PollPollTheme.colors.primary_050 else PollPollTheme.colors.gray_050
        val circleColor = if (selected) PollPollTheme.colors.primary_500 else PollPollTheme.colors.gray_400
        val iconColor = if (selected) PollPollTheme.colors.primary_050 else PollPollTheme.colors.gray_400

        Column(modifier = Modifier
            .padding(top = 20.dp)
            .clip(RoundedCornerShape(12.dp))
            .height(94.dp)
            .width(100.dp)
            .background(color = bgColor),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.size(32.dp))
            Text(text = "내가 쓴 투표", style = PollPollTheme.typography.body02, color = contentColor)
            Spacer(Modifier.size(5.dp))
            Row() {
                Text(text = "0", style = PollPollTheme.typography.heading05,
                    modifier = Modifier.alignByBaseline())
                Spacer(Modifier.size(3.dp))
                Text(text = "개", style = PollPollTheme.typography.body04,
                    color = PollPollTheme.colors.gray_500, modifier = Modifier.alignByBaseline())
            }
        }

        Box(modifier = Modifier
            .clip(CircleShape)
            .size(40.dp)
            .background(color = contentColor)
            .align(Alignment.TopCenter)) {
            Icon(
                painter = painterResource(id = PollIcon.Close),
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
fun PollCard(modifier: Modifier) {
    Card(
        modifier = modifier, shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        
    }
}



// Previews

@Preview(showBackground = true)
@Composable
private fun PollRecordPreview() {
    PollPollTheme {
        MypollpollScreen()
    }
}
