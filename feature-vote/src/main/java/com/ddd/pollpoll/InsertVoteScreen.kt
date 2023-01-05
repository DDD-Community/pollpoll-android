package com.ddd.pollpoll

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddd.pollpoll.designsystem.component.PollIconButtonText
import com.ddd.pollpoll.designsystem.component.PollProgressBar
import com.ddd.pollpoll.designsystem.component.PollTextField
import com.ddd.pollpoll.designsystem.component.PollTopBar
import com.ddd.pollpoll.designsystem.core.grid.VerticalGrid
import com.ddd.pollpoll.designsystem.icon.PollIcon
import com.ddd.pollpoll.designsystem.theme.PollPollTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertVoteScreen() {
    Scaffold(topBar = {
        Column() {
            PollTopBar(
                title = {
                    Text(text = "투표 작성")
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
            PollProgressBar()
        }
    }) {
        Surface(modifier = Modifier.padding(it)) {
            ChoiceCategoryScreen(
                onClick = {}
            )
        }
    }
}

@Composable
fun ChoiceCategoryScreen(onClick: (Category) -> Unit = {}) {
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
                    onClick = { onClick(it) },
                    text = it.text
                )
            }
        }
    }
}

@Composable
fun InsertContentScreen(@DrawableRes selectIcon: Int = PollIcon.Buy) {
    Column(Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(36.dp))
        Row(
            modifier = Modifier.align(Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = selectIcon), contentDescription = "")
            Text(text = "이직")
        }
        Spacer(modifier = Modifier.height(24.dp))
        PollTextField(text = "하하하하하하하하하하", placeholderText = "")
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
fun InsertContentScreenPreview() {
    PollPollTheme() {
        InsertContentScreen(PollIcon.Buy)
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun ChoiceCategoryScreenPortraitPreview() {
    PollPollTheme() {
        ChoiceCategoryScreen()
    }
}
