package com.ddd.pollpoll

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddd.pollpoll.core.ui.grid.VerticalGrid
import com.ddd.pollpoll.designsystem.component.PollIconButton
import com.ddd.pollpoll.designsystem.component.PollIconButtonText
import com.ddd.pollpoll.designsystem.component.PollProgressBar
import com.ddd.pollpoll.designsystem.component.PollTopBar
import com.ddd.pollpoll.designsystem.theme.PollPollTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertVoteScreen() {
    Scaffold(topBar = {
        Column() {
            PollTopBar(title = "투표작성")
            PollProgressBar()
        }
    }) {
        Surface(modifier = Modifier.padding(it)) {
            ChoiceCategoryScreen()
        }
    }
}

@Composable
fun ChoiceCategoryScreen() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "카테고리를 설정해주세요")
        Spacer(modifier = Modifier.height(48.dp))
        VerticalGrid() {
            CategoryList.forEach { it ->
                PollIconButtonText(
                    iconRes = it.iconDrawable,
                    contentDes = "",
                    isClicked = false,
                    onClick = {},
                    text = it.text
                )
            }
        }
    }
}

@Preview
@Composable
fun InsertVoteScreenPreview() {
    PollPollTheme() {
        InsertVoteScreen()
    }
}

@Preview
@Composable
fun ChoiceCategoryScreenPreview() {
    PollPollTheme {
        ChoiceCategoryScreen()
    }
}


@Preview(showBackground = true, widthDp = 480)
@Composable
private fun ChoiceCategoryScreenPortraitPreview() {
    PollPollTheme() {
        ChoiceCategoryScreen()
    }
}