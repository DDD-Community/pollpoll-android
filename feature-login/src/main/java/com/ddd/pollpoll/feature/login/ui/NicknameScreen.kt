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

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddd.pollpoll.feature.login.R
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ddd.pollpoll.designsystem.component.PollButton
import com.ddd.pollpoll.designsystem.icon.PollIcon
import com.ddd.pollpoll.designsystem.theme.PollPollTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
internal fun NicknameRoute(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: NicknameViewModel = hiltViewModel(),
    navigateToMain: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val localView = LocalView.current
    val googleSignInClient = getGoogleNicknameAuth(localView.context as Activity)

    val startForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (result.data != null) {
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(intent)
                    handleSignInResult(task, viewModel)
                }
            }
        }

    NicknameScreen(
        modifier = modifier,
        nicknameClick = {
            startForResult.launch(googleSignInClient?.signInIntent)
        },
        uiState,
        navigateToMain = navigateToMain
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NicknameScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    nicknameClick: () -> Unit = {},
    uiState: NicknameUiState,
    navigateToMain: (String) -> Unit
) {
    when (uiState) {
        is NicknameUiState.Success -> {
            navigateToMain(uiState.data)
        }
        NicknameUiState.Empty -> {}
        is NicknameUiState.Error -> {}
        NicknameUiState.Loading -> {}
    }

    Surface(modifier) {
        Column(Modifier.background(Color.White) ,horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.img_speech_bubble),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                style = PollPollTheme.typography.heading01,
                text = "오늘도 고민거리를\n해결하고 가세요!",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(40.dp))
            OutlinedTextField(
                modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
                value = "멋쟁이 다람쥐",
                shape = RoundedCornerShape(12.dp),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = PollIcon.Close),
                        contentDescription = "",
                        Modifier.size(18.dp),
                        tint = PollPollTheme.colors.gray_500
                    )
                },
                textStyle = PollPollTheme.typography.heading05,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = PollPollTheme.colors.gray_300,
                    unfocusedBorderColor = PollPollTheme.colors.gray_300,
                    containerColor = PollPollTheme.colors.gray_050),
                onValueChange = {

            })
            Spacer(modifier = Modifier.height(40.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = PollIcon.Search), contentDescription = "", Modifier.size(18.dp), tint = PollPollTheme.colors.primary_500)
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "다시 추천받기", style = PollPollTheme.typography.body02, color = PollPollTheme.colors.primary_500)
            }
            Spacer(modifier = Modifier.height(85.dp))

            PollButton(onClick = nicknameClick) {
                Text(text = "폴폴 들어가기", style = PollPollTheme.typography.heading05)
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

private fun getGoogleNicknameAuth(activity: Activity): GoogleSignInClient {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestIdToken("182245619639-91irinookimtn557n057f2vcg9pn7okb.apps.googleusercontent.com")
        .requestId()
        .requestProfile()
        .build()
    return GoogleSignIn.getClient(activity, gso)
}

private fun handleSignInResult(
    completedTask: Task<GoogleSignInAccount>,
    viewModel: NicknameViewModel
) {
    try {
        val account = completedTask.getResult(ApiException::class.java)
        account.idToken?.let { token -> viewModel.addNickname(token) }
    } catch (e: ApiException) {
        Log.w("Test", "signInResult:failed code=" + e.getStatusCode())
    }
}
// Previews

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    PollPollTheme {
        NicknameScreen(modifier = Modifier, uiState = NicknameUiState.Success("gf"), navigateToMain = {})
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    PollPollTheme() {
        NicknameScreen(modifier = Modifier, uiState = NicknameUiState.Success("gf"), navigateToMain = {})
    }
}
