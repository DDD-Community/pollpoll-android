package com.ddd.pollpoll.feature.vote

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ddd.pollpoll.designsystem.component.PollIconButtonText
import com.ddd.pollpoll.designsystem.component.PollProgressBar
import com.ddd.pollpoll.designsystem.component.PollTextField
import com.ddd.pollpoll.designsystem.component.PollTopBar
import com.ddd.pollpoll.designsystem.core.grid.VerticalGrid
import com.ddd.pollpoll.designsystem.icon.PollIcon
import com.ddd.pollpoll.designsystem.theme.PollPollTheme

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
internal fun InsertVoteRoute(
    modifier: Modifier = Modifier,
    viewModel: InsertVoteViewModel = hiltViewModel()
) {
    val uiState: InsertVoteUiState by viewModel.uiState.collectAsStateWithLifecycle()

    val textState: FormData by viewModel.textField.collectAsStateWithLifecycle()

    InsertVoteScreen(
        modifier = modifier,
        textFormData = textState,
        uiState = uiState,
        chooseCategory = viewModel::selectCategory,
        titleValueChange = viewModel::insertTitle,
        contentValueChange = viewModel::insertContent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertVoteScreen(
    modifier: Modifier,
    textFormData: FormData,
    uiState: InsertVoteUiState,
    chooseCategory: (Category) -> Unit,
    titleValueChange: (String) -> Unit,
    contentValueChange: (String) -> Unit
) {
    Scaffold(modifier = modifier, topBar = { VoteTopBar() }) {
        Surface(modifier = Modifier.padding(it)) {
            when (uiState) {
                InsertVoteUiState.SelectCategory -> {
                    ChoiceCategoryScreen(onClick = chooseCategory)
                }
                is InsertVoteUiState.InsertTitle -> {
                    InsertContentScreen(
                        textFormData.category,
                        textFormData.title,
                        titleValueChange = titleValueChange,
                        contentValueChange = contentValueChange
                    )
                }
            }
        }
    }
}

@Composable
private fun VoteTopBar() {
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
}

@Composable
fun ChoiceCategoryScreen(onClick: (Category) -> Unit = {}) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "카테고리를 설정해주세요")
        Spacer(modifier = Modifier.height(48.dp))
        VerticalGrid() {
            CategoryList.forEach {
                PollIconButtonText(
                    iconRes = it.iconDrawable,
                    contentDes = "",
                    isClicked = false,
                    onClick = {
                        onClick(it)
                    },
                    text = it.text
                )
            }
        }
    }
}

@Composable
fun InsertContentScreen(
    category: Category = defalutCategory,
    title: String = "테스트 입니당",
    content: String = "콘텐츠 테스트 입니당",
    titleValueChange: (String) -> Unit = {},
    contentValueChange: (String) -> Unit = {}
) {
    var isContent by remember { mutableStateOf(false) }
    Column(Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(36.dp))
        Row(
            modifier = Modifier.align(Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = category.iconDrawable), contentDescription = "")
            Text(text = category.text)
        }
        Spacer(modifier = Modifier.height(24.dp))
        PollTextField(
            text = title,
            placeholderText = "제목을 입력해주세요",
            onValueChange = titleValueChange,
            keyboardActions = KeyboardActions {
                if (this == KeyboardActions.Default.onDone) isContent = true
            }
        )
        if (isContent) {
            PollTextField(
                text = content,
                placeholderText = "내용을 입력해주세요",
                onValueChange = contentValueChange,
                keyboardActions = KeyboardActions {
                    if (this == KeyboardActions.Default.onDone) isContent = true
                }
            )
        }
    }
}

@Preview
@Composable
fun InsertVoteScreenPreview() {
    PollPollTheme() {
//        InsertVoteScreen(modifier, uiState)
    }
}

@Preview(showBackground = true)
@Composable
fun InsertContentScreenPreview() {
    PollPollTheme() {
        InsertContentScreen()
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun ChoiceCategoryScreenPortraitPreview() {
    PollPollTheme() {
        ChoiceCategoryScreen()
    }
}
