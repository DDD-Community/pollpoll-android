package com.ddd.pollpoll.feature.vote

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
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

    val textState: AddingVote by viewModel.textField.collectAsStateWithLifecycle()

    InsertVoteScreen(
        modifier = modifier,
        textFormData = textState,
        uiState = uiState,
        chooseCategory = viewModel::selectCategory,
        titleValueChange = viewModel::insertTitle,
        contentValueChange = viewModel::insertContent,
        addVoteClicked = viewModel::navigateAddVoteCategory
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertVoteScreen(
    modifier: Modifier,
    textFormData: AddingVote,
    uiState: InsertVoteUiState,
    chooseCategory: (Category) -> Unit,
    titleValueChange: (String) -> Unit,
    contentValueChange: (String) -> Unit,
    addVoteClicked: () -> Unit = {}
) {
    var progressState by remember { mutableStateOf(0.0f) }

    Scaffold(modifier = modifier, topBar = { VoteTopBar(progressState) }) {
        Surface(modifier = Modifier.padding(it)) {
            when (uiState) {
                InsertVoteUiState.SelectCategory -> {
                    ChoiceCategoryScreen(onClick = chooseCategory)
                }

                is InsertVoteUiState.InsertTitle -> {
                    InsertContentScreen(
                        textFormData.category,
                        textFormData.title,
                        textFormData.content,
                        titleValueChange = titleValueChange,
                        contentValueChange = contentValueChange,
                        progressBarChanged = {
                            progressState = it.progressBar
                        },
                        addVoteClicked = addVoteClicked
                    )
                }

                InsertVoteUiState.AddVoteCategory -> {
                    AddVoteCategoryScreen(
                        textFormData.category
                    )
                }
            }
        }
    }
}

@Composable
fun AddVoteCategoryScreen(category: Category = defalutCategory) {
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .padding(20.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CategoryScreen(category)
        Column() {
            PollTextField(
                text = "",
                placeholderText = "항목입력",
                onValueChange = {}
            )
        }
        Spacer(modifier = Modifier.height(30.dp))

        Row(modifier = Modifier.align(Alignment.Start)) {
            Image(painter = painterResource(id = PollIcon.AddCircleGray), contentDescription = "")
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "항목 추가", style = PollPollTheme.typography.body02)
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(130.dp))
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(PollPollTheme.colors.gray_100))
        Box(modifier = Modifier.fillMaxWidth()){
            Text(modifier = Modifier.align(Alignment.CenterStart),text = "투표기간" , style = PollPollTheme.typography.heading05 , color = PollPollTheme.colors.gray_900)
            Row(modifier = Modifier.align(Alignment.TopEnd)) {
                Text(modifier = Modifier.align(Alignment.CenterVertically), text = "3일", style = PollPollTheme.typography.body02)
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = ImageVector.vectorResource(id = PollIcon.ChevronRight), contentDescription ="" )
                }
            }
        }
        Text(modifier = Modifier.align(alignment = Alignment.Start),text = "투표가 진행되고 있는 시점부터는 투표를 수정할 수 없습니다" , style = PollPollTheme.typography.body04 , color = PollPollTheme.colors.gray_400)

    }
}

@Composable
private fun VoteTopBar(progress: Float) {
    val progressAni by animateFloatAsState(progress)
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
        PollProgressBar(progress = progressAni)
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
    contentValueChange: (String) -> Unit = {},
    progressBarChanged: (VoteScreenEnum) -> Unit = {},
    addVoteClicked: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .padding(20.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CategoryScreen(category)
        Spacer(modifier = Modifier.height(24.dp))
        PollTextField(
            text = title,
            placeholderText = "제목을 입력해주세요",
            onValueChange = titleValueChange,
            maxLength = 50
        )
        if (title.isNotEmpty()) {
            progressBarChanged(VoteScreenEnum.Title)
            PollTextField(
                text = content,
                placeholderText = "내용을 입력해주세요",
                onValueChange = contentValueChange,
                maxLength = 200
            )
        }
        if (content.isNotEmpty()) {
            Spacer(modifier = Modifier.height(40.dp))
            AddVoteItemScreen(addVoteClicked = addVoteClicked)
            progressBarChanged(VoteScreenEnum.Content)
        }
    }
}

@Composable
private fun ColumnScope.CategoryScreen(category: Category) {
    Spacer(modifier = Modifier.height(36.dp))
    Row(
        modifier = Modifier.align(Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(id = category.iconDrawable), contentDescription = "")
        Text(text = category.text)
    }
}

@Composable
fun AddVoteItemScreen(addVoteClicked: () -> Unit = {}) {
    Surface(
        modifier = Modifier
            .size(320.dp, 62.dp)
            .clickable { addVoteClicked() },
        color = PollPollTheme.colors.primary_100
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(13.dp))
            Image(painter = painterResource(id = PollIcon.AddCircleRed), contentDescription = "")
            Spacer(modifier = Modifier.width(11.dp))
            Text(text = "투표 항목 추가하기")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddVoteCategoryPreview() {
    PollPollTheme() {
        AddVoteCategoryScreen()
    }
}

@Preview
@Composable
fun AddVoteItemScreenPreview() {
    PollPollTheme() {
        AddVoteItemScreen()
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
        InsertContentScreen(title = "", content = "")
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun ChoiceCategoryScreenPortraitPreview() {
    PollPollTheme() {
        ChoiceCategoryScreen()
    }
}

enum class VoteScreenEnum(val progressBar: Float) {
    Title(0.2f),
    Content(0.4f),
    AddVote(0.6f),
}
