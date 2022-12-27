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
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.repeatOnLifecycle
import com.ddd.pollpoll.designsystem.component.PollLoginButton
import com.ddd.pollpoll.designsystem.theme.PollPollTheme
import com.ddd.pollpoll.feature.login.R
import com.ddd.pollpoll.feature.login.ui.LoginUiState.Success

@Composable
fun LoginScreen(modifier: Modifier = Modifier, viewModel: LoginViewModel = hiltViewModel()) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val items by produceState<LoginUiState>(
        initialValue = LoginUiState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }
    if (items is Success) {
        LoginScreen(
            items = (items as Success).data,
            onSave = { name -> viewModel.addLogin(name) },
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LoginScreen(
    items: List<String>,
    onSave: (name: String) -> Unit,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    Surface(modifier) {
        Box(contentAlignment = Alignment.TopCenter) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(80.dp))
                Image(
                    painter = painterResource(id = R.drawable.img_speech_bubble),
                    contentDescription = ""
                )
                Text(

                    style = PollPollTheme.typography.heading01,
                    text = buildAnnotatedString {
                        append("고민이 있나요?")
                        withStyle(
                            SpanStyle(
                                color = PollPollTheme.colors.primary_500
                            )
                        ) {
                            append("\n폴폴")
                        }
                        append("이 도와줄게요.")
                    },
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = "모든 고민거리는 투표를 통해 해결해요", style = PollPollTheme.typography.body02)
            }
            Column(Modifier.align(Alignment.BottomCenter)) {
                PollLoginButton(text = "구글 ID 로그인")
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

// Previews

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    PollPollTheme {
        LoginScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    PollPollTheme() {
        LoginScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
    }
}
