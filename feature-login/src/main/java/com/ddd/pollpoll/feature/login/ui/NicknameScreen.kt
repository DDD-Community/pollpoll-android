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

package com.ddd.pollpoll.feature.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ddd.pollpoll.designsystem.component.PollButton
import com.ddd.pollpoll.designsystem.icon.PollIcon
import com.ddd.pollpoll.designsystem.theme.PollPollTheme
import com.ddd.pollpoll.feature.login.R

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
internal fun NicknameRoute(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: NicknameViewModel = hiltViewModel(),
    navigateToMainScreen: () -> Unit = {},
) {
    val recommendNicknameUiState by viewModel.recommendNicknameUiState.collectAsStateWithLifecycle()
    val insertNicknameUiState by viewModel.insertNicknameUiState.collectAsStateWithLifecycle()

    NicknameScreen(
        modifier = modifier,
        navigateToMain = navigateToMainScreen,
        insertNicknameUiState = insertNicknameUiState,
        recommendNicknameUiState = recommendNicknameUiState,
        onRecommendButtonClicked = { viewModel.recommendNickname() },
        onInsertNickNameButtonClicked = { viewModel.insertNickName(it) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NicknameScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    recommendNicknameUiState: RecommendNicknameUiState,
    insertNicknameUiState: InsertNicknameUiState,
    navigateToMain: () -> Unit,
    onRecommendButtonClicked: () -> Unit,
    onInsertNickNameButtonClicked: (String) -> Unit,
) {
    var nickNameState by remember { mutableStateOf("") }

    when (recommendNicknameUiState) {
        RecommendNicknameUiState.Empty -> {}
        is RecommendNicknameUiState.Error -> {}
        RecommendNicknameUiState.Loading -> {}
        is RecommendNicknameUiState.Success -> {
            nickNameState = recommendNicknameUiState.data
        }
    }

    when (insertNicknameUiState) {
        InsertNicknameUiState.Empty -> {}
        is InsertNicknameUiState.Error -> {}
        InsertNicknameUiState.Loading -> {}
        InsertNicknameUiState.Success -> {
            navigateToMain()
        }
    }

    Surface(modifier) {
        Column(
            Modifier.background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.img_speech_bubble),
                contentDescription = "",
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                style = PollPollTheme.typography.heading01,
                text = "오늘도 고민거리를\n해결하고 가세요!",
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(40.dp))
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                value = nickNameState,
                shape = RoundedCornerShape(12.dp),
                trailingIcon = {
                    IconButton(onClick = { nickNameState = "" }) {
                        Icon(
                            painter = painterResource(id = PollIcon.Close),
                            contentDescription = "",
                            Modifier.size(18.dp),
                            tint = PollPollTheme.colors.gray_500,
                        )
                    }
                },
                textStyle = PollPollTheme.typography.heading05,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = PollPollTheme.colors.gray_300,
                    unfocusedBorderColor = PollPollTheme.colors.gray_300,
                    containerColor = PollPollTheme.colors.gray_050,
                ),
                onValueChange = { nickNameState = it },
            )
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier.wrapContentSize().clickable { onRecommendButtonClicked() },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = PollIcon.Search),
                    contentDescription = "",
                    Modifier.size(18.dp),
                    tint = PollPollTheme.colors.primary_500,
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "다시 추천받기",
                    style = PollPollTheme.typography.body02,
                    color = PollPollTheme.colors.primary_500,
                )
            }
            Spacer(modifier = Modifier.height(85.dp))

            PollButton(
                modifier = Modifier,
                shape = RoundedCornerShape(20.dp),
                onClick = { onInsertNickNameButtonClicked(nickNameState) },
                contentPadding = PaddingValues(horizontal = 97.dp, vertical = 10.dp),
            ) {
                Text(text = "폴폴 들어가기", style = PollPollTheme.typography.heading05)
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    PollPollTheme {
        NicknameScreen(
            modifier = Modifier,
            recommendNicknameUiState = RecommendNicknameUiState.Empty,
            insertNicknameUiState = InsertNicknameUiState.Empty,
            navigateToMain = {},
            onRecommendButtonClicked = {},
            onInsertNickNameButtonClicked = {},
        )
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    PollPollTheme {
        NicknameScreen(
            modifier = Modifier,
            recommendNicknameUiState = RecommendNicknameUiState.Empty,
            insertNicknameUiState = InsertNicknameUiState.Empty,
            navigateToMain = {},
            onRecommendButtonClicked = {},
            onInsertNickNameButtonClicked = {},
        )
    }
}
