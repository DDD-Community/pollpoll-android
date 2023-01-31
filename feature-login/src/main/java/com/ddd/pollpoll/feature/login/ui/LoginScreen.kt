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
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ddd.pollpoll.designsystem.component.PollLoginButton
import com.ddd.pollpoll.designsystem.theme.PollPollTheme
import com.ddd.pollpoll.feature.login.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
internal fun LoginRoute(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToMain: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val localView = LocalView.current
    val googleSignInClient = getGoogleLoginAuth(localView.context as Activity)

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

    LoginScreen(
        modifier = modifier,
        loginClick = {
            startForResult.launch(googleSignInClient?.signInIntent)
        },
        uiState,
        navigateToMain = navigateToMain
    )
}

@Composable
internal fun LoginScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    loginClick: () -> Unit = {},
    uiState: LoginUiState,
    navigateToMain: (String) -> Unit
) {
    when (uiState) {
        is LoginUiState.Success -> {
            navigateToMain(uiState.data)
        }
        LoginUiState.Empty -> {}
        is LoginUiState.Error -> {}
        LoginUiState.Loading -> {}
    }

    Surface(modifier) {
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
            Spacer(modifier = Modifier.height(153.dp))
            PollLoginButton(text = "구글 ID 로그인", onClick = loginClick)
        }
    }
}

private fun getGoogleLoginAuth(activity: Activity): GoogleSignInClient {
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
    viewModel: LoginViewModel
) {
    try {
        val account = completedTask.getResult(ApiException::class.java)
        account.idToken?.let { token -> viewModel.addLogin(token) }
    } catch (e: ApiException) {
        Log.w("Test", "signInResult:failed code=" + e.getStatusCode())
    }
}
// Previews

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    PollPollTheme {
        LoginScreen(modifier = Modifier, uiState = LoginUiState.Success("gf"), navigateToMain = {})
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    PollPollTheme() {
        LoginScreen(modifier = Modifier, uiState = LoginUiState.Success("gf"), navigateToMain = {})
    }
}
