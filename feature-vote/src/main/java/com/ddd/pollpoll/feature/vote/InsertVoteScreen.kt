package com.ddd.pollpoll.feature.vote

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ddd.pollpoll.PollItem
import com.ddd.pollpoll.designsystem.component.PollAlertDialog
import com.ddd.pollpoll.designsystem.component.PollButton
import com.ddd.pollpoll.designsystem.component.PollIconButtonText
import com.ddd.pollpoll.designsystem.component.PollProgressBar
import com.ddd.pollpoll.designsystem.component.PollRadioButton
import com.ddd.pollpoll.designsystem.component.PollInputTextField
import com.ddd.pollpoll.designsystem.component.PollTopBar
import com.ddd.pollpoll.designsystem.core.bottomseat.PollModalBottomSheetLayout
import com.ddd.pollpoll.designsystem.core.grid.VerticalGrid
import com.ddd.pollpoll.designsystem.icon.PollIcon
import com.ddd.pollpoll.designsystem.theme.PollPollTheme
import com.theapache64.rebugger.Rebugger
import kotlinx.coroutines.launch

@Composable
internal fun InsertVoteRoute(
    modifier: Modifier = Modifier,
    viewModel: InsertVoteViewModel = hiltViewModel(),
    onCloseButtonClicked: () -> Unit,
    onInsertSucceed: () -> Unit,
) {
    val uiState: InsertVoteUiState by viewModel.uiState.collectAsStateWithLifecycle()
    InsertVoteScreen(
        modifier = modifier,
        insertVoteUiState = uiState,
        chooseCategory = viewModel::selectCategory,
        titleValueChange = viewModel::changeTitle,
        contentValueChange = viewModel::changeContent,
        onTextChanged = { index: Int, text: String ->
            viewModel.changeVoteList(index, PollItem(text))
        },
        onAddCategory = viewModel::addVoteList,
        onCloseButtonClicked = onCloseButtonClicked,
        onInsertButtonClicked = viewModel::insertPost,
        onVoteDateSelected = viewModel::changeDate,
        onInsertSucceed = onInsertSucceed,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertVoteScreen(
    modifier: Modifier,
    insertVoteUiState: InsertVoteUiState,
    chooseCategory: (Category) -> Unit,
    titleValueChange: (String) -> Unit,
    contentValueChange: (String) -> Unit,
    onAddCategory: () -> Unit,
    onTextChanged: (index: Int, String) -> Unit,
    onCloseButtonClicked: () -> Unit = {},
    onInsertButtonClicked: () -> Unit = {},
    onVoteDateSelected: (Long) -> Unit = {},
    onInsertSucceed: () -> Unit = {},
) {
    val insertAppState = rememberInsertVoteState()
    val insetVoteStepState = rememberInsertVoteStepState()

    Rebugger(
        trackMap = mapOf(
            "insertAppState" to insertAppState,
        ),
    )
    var selectedOptionState by remember { mutableStateOf(VoteRadioList[2]) }
    var writeEnabledState by rememberSaveable { mutableStateOf(false) }
    var contentEnabledState by rememberSaveable { mutableStateOf(false) }
    var voteEnabledState by rememberSaveable { mutableStateOf(false) }

    if (insertAppState.bottomState) {
        PollModalBottomSheetLayout(
            sheetState = insertAppState.bottomSheetState,
            onDismissRequest = {
                insertAppState.openBottomSheet(false)
            },

        ) {
            Column() {
                VoteRadioList.forEach {
                    SelectDateScreen(
                        it,
                        selectedOptionState,
                        onClick = { voteDate ->
                            selectedOptionState = voteDate
                            onVoteDateSelected(voteDate.time)
                        },
                    )
                }
            }
        }
    }

    BackHandler(enabled = true) {
        with(insertAppState) {
            if (insertAppState.bottomSheetState.isVisible) {
                coroutineScope.launch { bottomSheetState.hide() }
            } else {
                insetVoteStepState.backInsertScreen()
            }
        }
    }

    // 게시글 작성에 성공했는지
    if (insertVoteUiState.isInsertSuccess) onInsertSucceed()

    if (insertAppState.confirmDialogState) {
        PollAlertDialog(
            onDismissRequest = { insertAppState.setShowingConfirmDialog(false) },
            onCancelClicked = { insertAppState.setShowingConfirmDialog(false) },
            onConfirmClicked = { onInsertButtonClicked() },
        )
    }

    if (insertAppState.cancelDialogState) {
        PollAlertDialog(
            onDismissRequest = { insertAppState.setShowingCancelDialog(false) },
            onCancelClicked = { insertAppState.setShowingCancelDialog(false) },
            onConfirmClicked = { onCloseButtonClicked() },
            title = "등록을 취소할까요?",
            content = "지금까지 작성한 건 삭제가 됩니다.",
        )
    }

    Scaffold(modifier = modifier, topBar = {
        VoteTopBar(
            insertAppState.progressState,
            onBackButtonClicked = {
                insetVoteStepState.backInsertScreen()
            },
            onCloseButtonClicked = {
                insertAppState.setShowingCancelDialog(true)
            },
        )
    }) { scaffoldPadding ->
        Surface(modifier = Modifier.padding(scaffoldPadding)) {
            when (insetVoteStepState.insertVoteStep) {
                InsertVoteStep.SelectCategory -> {
                    ChoiceCategoryScreen(onClick = {
                        chooseCategory(it)
                        insetVoteStepState.navigateInsertTitle()
                    })
                }

                InsertVoteStep.InsertTitle -> {
                    InsertContentScreen(
                        insertVoteUiState.category.toCategory(),
                        insertVoteUiState.title,
                        insertVoteUiState.contents,
                        titleValueChange = titleValueChange,
                        contentValueChange = contentValueChange,
                        progressBarChanged = { insertAppState.setProgressBar(it.progressBar) },
                        addVoteClicked = { insetVoteStepState.navigateAddVoteCategory() },
                        isWriteEnabled = writeEnabledState,
                        contentEnabled = contentEnabledState,
                        voteEnabled = voteEnabledState,
                        onContentDone = { voteEnabledState = true },
                        onTitleDone = { contentEnabledState = true },
                        onVoteCompleteButtonClicked = {
                            insertAppState.setShowingConfirmDialog(
                                true,
                            )
                        },

                    )
                }

                InsertVoteStep.AddVoteCategory -> {
                    AddVoteCategoryScreen(
                        insertVoteUiState.category.toCategory(),
                        onDialogClick = {
                            insertAppState.coroutineScope.launch {
                                insertAppState.openBottomSheet(true)
                                insertAppState.bottomSheetState.show()
                            }
                        },
                        selectedDate = selectedOptionState,
                        onAddCategory = onAddCategory,
                        onTextChanged = onTextChanged,
                        voteList = insertVoteUiState.pollItems,
                        progressBarChanged = {
                            insertAppState.setProgressBar(it.progressBar)
                        },
                        onVoteButtonClicked = {
                            writeEnabledState = true
                            insetVoteStepState.backInsertScreen()
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun AddVoteCategoryScreen(
    category: Category = defalutCategory,
    onDialogClick: () -> Unit = {},
    selectedDate: VoteRadioButton,
    onAddCategory: () -> Unit = {},
    onTextChanged: (index: Int, String) -> Unit,
    voteList: List<PollItem> = listOf(),
    onVoteButtonClicked: () -> Unit = {},
    progressBarChanged: (VoteScreenEnum) -> Unit,
) {
    var buttonEnabled by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    buttonEnabled = !voteList.contains(PollItem(""))
    Column(
        Modifier
            .fillMaxHeight()
            .verticalScroll(scrollState)
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(Modifier.padding(20.dp)) {
            CategoryScreen(category)
            voteList.forEachIndexed { index, it ->
                PollInputTextField(text = it.name, placeholderText = "항목입력", onValueChange = {
                    onTextChanged(index, it)
                })
                Spacer(modifier = Modifier.height(20.dp))
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier
                    .align(Alignment.Start)
                    .clickable { onAddCategory() },
            ) {
                Image(
                    painter = painterResource(id = PollIcon.AddCircleGray),
                    contentDescription = "",
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "항목 추가", style = PollPollTheme.typography.body02)
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(PollPollTheme.colors.gray_100),
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.align(Alignment.CenterStart),
                    text = "투표기간",
                    style = PollPollTheme.typography.heading05,
                    color = PollPollTheme.colors.gray_900,
                )
                Row(modifier = Modifier.align(Alignment.TopEnd)) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = selectedDate.date,
                        style = PollPollTheme.typography.body02,
                    )
                    IconButton(onClick = { onDialogClick() }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = PollIcon.ChevronRight),
                            contentDescription = "투표기간 설정하기 버튼",
                        )
                    }
                }
            }
            Text(
                modifier = Modifier.align(alignment = Alignment.Start),
                text = "투표가 진행되고 있는 시점부터는 투표를 수정할 수 없습니다",
                style = PollPollTheme.typography.body04,
                color = PollPollTheme.colors.gray_400,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        PollButton(
            Modifier
                .fillMaxWidth()
                .heightIn(min = 60.dp),
            enabled = buttonEnabled,
            onClick = {
                onVoteButtonClicked()
                progressBarChanged(VoteScreenEnum.AddVote)
            },
        ) {
            Text(text = "작성완료", style = PollPollTheme.typography.heading05)
        }
    }
}

@Composable
private fun VoteTopBar(
    progress: Float,
    onBackButtonClicked: () -> Unit = {},
    onCloseButtonClicked: () -> Unit = {},
) {
    val progressAni by animateFloatAsState(progress)
    Column(Modifier.background(Color.White)) {
        PollTopBar(
            title = {
                Text(text = "투표 작성")
            },
            navigationIconColor = Color.Black,
            titleContentColor = PollPollTheme.colors.gray_900,
            actionIconColor = Color.Black,

            navigationIcon = {
                IconButton(onClick = onBackButtonClicked) {
                    Icon(
                        painter = painterResource(id = PollIcon.LeftArrow),
                        contentDescription = "",
                    )
                }
            },
            actions = {
                IconButton(onClick = onCloseButtonClicked) {
                    Icon(
                        painter = painterResource(id = PollIcon.Close),
                        contentDescription = "",
                    )
                }
            },
        )
        PollProgressBar(progress = progressAni)
    }
}

@Composable
fun ChoiceCategoryScreen(onClick: (Category) -> Unit = {}) {
    Column(Modifier.background(Color.White), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "카테고리를 설정해주세요")
        Spacer(modifier = Modifier.height(48.dp))
        VerticalGrid() {
            CategoryList.forEach {
                PollIconButtonText(
                    iconRes = it.iconDrawable,
                    contentDescription = "${it.text} 버튼",
                    isClicked = false,
                    onClick = {
                        onClick(it)
                    },
                    text = it.text,
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
    contentEnabled: Boolean = false,
    voteEnabled: Boolean = false,
    titleValueChange: (String) -> Unit = {},
    contentValueChange: (String) -> Unit = {},
    progressBarChanged: (VoteScreenEnum) -> Unit = {},
    addVoteClicked: () -> Unit = {},
    isWriteEnabled: Boolean = false,
    onVoteCompleteButtonClicked: () -> Unit = {},
    onTitleDone: () -> Unit,
    onContentDone: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Column(
            Modifier
                .padding(20.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CategoryScreen(category)
            PollInputTextField(
                text = title,
                placeholderText = "제목을 입력해주세요",
                onValueChange = titleValueChange,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onTitleDone()
                        progressBarChanged(VoteScreenEnum.Title)
                        focusManager.clearFocus()
                    },
                ),
                maxLength = 50,
            )
            if (contentEnabled) {
                PollInputTextField(
                    text = content,
                    placeholderText = "내용을 입력해주세요",
                    onValueChange = contentValueChange,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onContentDone()
                            progressBarChanged(VoteScreenEnum.Content)
                            focusManager.clearFocus()
                        },
                    ),
                    maxLength = 200,
                )
            }
            if (voteEnabled) {
                Spacer(modifier = Modifier.height(40.dp))
                AddVoteItemScreen(addVoteClicked = addVoteClicked, isComplete = isWriteEnabled)
            }
        }

        PollButton(
            Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .heightIn(min = 60.dp),
            enabled = isWriteEnabled,
            onClick = onVoteCompleteButtonClicked,
        ) {
            Text(text = "작성완료", style = PollPollTheme.typography.heading05)
        }
    }
}

@Composable
private fun ColumnScope.CategoryScreen(category: Category) {
    Row(
        modifier = Modifier.align(Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(painter = painterResource(id = category.iconDrawable), contentDescription = "")
        Text(text = category.text)
    }
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun AddVoteItemScreen(isComplete: Boolean, addVoteClicked: () -> Unit = {}) {
    Surface(
        modifier = Modifier
            .size(320.dp, 62.dp)
            .clickable { addVoteClicked() },
        color = PollPollTheme.colors.primary_100,
    ) {
        if (isComplete) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(13.dp))
                Image(
                    painter = painterResource(id = PollIcon.VoteModification),
                    contentDescription = "",
                )
                Spacer(modifier = Modifier.width(11.dp))
                Column() {
                    Text(
                        text = "투표 항목 수정하기",
                        style = PollPollTheme.typography.heading05,
                        color = PollPollTheme.colors.gray_700,
                    )
                    Text(
                        text = "글 등록 이후에는 투표를 수정할 수 없습니다.",
                        style = PollPollTheme.typography.body04,
                        color = PollPollTheme.colors.gray_400,
                    )
                }
            }
        } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(13.dp))
                Image(
                    painter = painterResource(id = PollIcon.AddCircleRed),
                    contentDescription = "",
                )
                Spacer(modifier = Modifier.width(11.dp))
                Text(
                    text = "투표 항목 추가하기",
                    style = PollPollTheme.typography.heading05,
                    color = PollPollTheme.colors.gray_700,
                )
            }
        }
    }
}

@Composable
fun SelectDateScreen(
    selectedOption: VoteRadioButton,
    seleted: VoteRadioButton,
    onClick: (VoteRadioButton) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(vertical = 20.dp)
            .semantics(true) {
                this.onClick(
                    label = "날짜 지정하기",
                    action = {
                        onClick(selectedOption)
                        true
                    },

                )
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.width(43.dp))
        PollRadioButton(
            Modifier.clearAndSetSemantics {},
            onClick = { onClick(selectedOption) },
            selected = selectedOption == seleted,
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = selectedOption.date)
    }
}

@Preview(showBackground = true)
@Composable
fun SelectDateScreenPreview() {
//    PollPollTheme() {
    SelectDateScreen(VoteRadioButton("test", 360000), VoteRadioButton("test", 3666)) {}
//    }
}

@Preview(showBackground = true)
@Composable
fun AddVoteCategoryPreview() {
    PollPollTheme() {
        AddVoteCategoryScreen(
            selectedDate = VoteRadioButton("test", 3600000),
            onTextChanged = { it, test -> },
            progressBarChanged = {},
        )
    }
}

@Preview
@Composable
fun AddVoteItemScreenPreview() {
    PollPollTheme() {
        AddVoteItemScreen(true)
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
        InsertContentScreen(
            title = "", content = "", isWriteEnabled = false,
            contentEnabled = false,
            voteEnabled = false,
            titleValueChange = {},
            contentValueChange = {},
            progressBarChanged = {},
            addVoteClicked = {},
            onTitleDone = {},
            onContentDone = {},
        )
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
    Title(0.2f), Content(0.4f), AddVote(1.0f),
}

val VoteRadioList = listOf(
    VoteRadioButton("3일", 36000000),
    VoteRadioButton("2일", 36000000),
    VoteRadioButton("1일", 36000000),
    VoteRadioButton("12시간", 36000000),
)

@Stable
data class VoteRadioButton(val date: String, val time: Long)
