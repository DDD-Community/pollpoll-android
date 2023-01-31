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

package com.ddd.pollpoll.feature.settings.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.pollpoll.designsystem.icon.PollIcon
import com.ddd.pollpoll.designsystem.theme.PollPollColors
import com.ddd.pollpoll.designsystem.theme.PollPollTheme

@Composable
fun SettingsScreen(modifier: Modifier = Modifier, viewModel: SettingsViewModel = hiltViewModel()) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(

) {
    Column(
        modifier = Modifier
            .background(color = PollPollTheme.colors.gray_050)
            .padding(horizontal = 20.dp)
    ) {
        SettingsCard(modifier = Modifier.fillMaxWidth()) {
            SettingCardItem(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                PollIcon.Question,
                "자주 묻는 질문",
                null
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        SettingsCard(modifier = Modifier.fillMaxWidth()) {
            SettingCardItem(
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = (12.5).dp)
                    .fillMaxWidth(), PollIcon.Privacy, "개인정보 처리방침", null
            )
            SettingCardItem(
                modifier = Modifier
                    .padding(top = (12.5).dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
                    .fillMaxWidth(), PollIcon.TermsOfService, "이용약관", null
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        SettingsCard(modifier = Modifier.fillMaxWidth()) {
            SettingCardItem(
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = (12.5).dp)
                    .fillMaxWidth(), PollIcon.VersionInfo, "버전정보", "1.0.0"
            )
            SettingCardItem(
                modifier = Modifier
                    .padding(top = (12.5).dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
                    .fillMaxWidth(), PollIcon.TermsOfService, "오픈소스 라이선스", null
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        SettingsCard(modifier = Modifier.fillMaxWidth()) {
            SettingCardItem(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                PollIcon.Question,
                "로그아웃",
                null
            )
        }
    }
}

@Composable
fun SettingsCard(modifier: Modifier, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = modifier, shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        content = content
    )
}

@Composable
fun SettingCardItem(
    modifier: Modifier,
    icResId: Int,
    label: String,
    subLabel: String?,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icResId),
            contentDescription = "",
            tint = PollPollTheme.colors.gray_300
        )
        Spacer(modifier = Modifier.size(15.dp))
        Text(text = label)
        Spacer(modifier = Modifier.weight(1f))
        subLabel?.let {
            Text(text = subLabel, color = PollPollTheme.colors.primary_500)
            Spacer(modifier = Modifier.size(15.dp))
        }
        Icon(
            painter = painterResource(id = PollIcon.ChevronRight),
            contentDescription = "",
            tint = PollPollTheme.colors.gray_400
        )

    }
}


// Previews

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    PollPollTheme() {
        SettingsScreen()
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    PollPollTheme() {
        SettingsScreen()
    }
}
