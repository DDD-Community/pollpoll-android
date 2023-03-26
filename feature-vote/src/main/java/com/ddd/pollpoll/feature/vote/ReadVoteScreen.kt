package com.ddd.pollpoll.feature.vote

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import com.ddd.pollpoll.core.network.model.PostResponse
import com.ddd.pollpoll.designsystem.component.PollButton
import com.ddd.pollpoll.designsystem.component.PollLabel
import com.ddd.pollpoll.designsystem.component.PollTopBar
import com.ddd.pollpoll.designsystem.icon.PollIcon
import com.ddd.pollpoll.designsystem.theme.PollPollTheme
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt
import kotlin.reflect.KFunction1

@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalMaterialApi::class)
@Composable
internal fun ReadVoteRoute(
    modifier: Modifier = Modifier,
    postId: Int,
    viewModel: ReadVoteViewModel = hiltViewModel(),
    onCloseButtonClicked: () -> Unit
) {
    ReadVoteScreen(viewModel, postId)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadVoteScreen(
    viewModel: ReadVoteViewModel = hiltViewModel(),
    postId: Int
) {
    val lastPost = viewModel.lastPost.collectAsState().value
    val selectedIndex = viewModel.selectedIndex.collectAsState().value
    val beforeVote = viewModel.beforeVote.collectAsState().value
    val afterVote = viewModel.afterVote.collectAsState().value
    viewModel.setPostId(postId)

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
        }
    }) {
        val scrollState = rememberScrollState()
        Surface(modifier = Modifier.padding(it)) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                VoteInfo(lastPost)
                Spacer(modifier = Modifier.size(20.dp))

                VoteContent(
                    lastPost, viewModel.voted.value,
                    viewModel::vote, viewModel::reVote, selectedIndex, viewModel::selectIndex,
                    beforeVote, afterVote)
            }
        }
    }
}

@Composable
fun VoteInfo(lastPost: PostResponse?) {
    if (lastPost == null) {
        Text(text = "loading...")
    } else {
        Row(verticalAlignment = Alignment.CenterVertically) {
            PollLabel("초이스")
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "${lastPost.categoryName}",
                color = PollPollTheme.colors.gray_900,
                style = PollPollTheme.typography.body04
            )
        }
        Spacer(modifier = Modifier.size(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "${lastPost.nickname}",
                color = PollPollTheme.colors.gray_900,
                style = PollPollTheme.typography.body03
            )
        }
        Spacer(modifier = Modifier.size(5.dp))
        Row() {
            TimeText(Date(lastPost.postCreatedAt))
            Text(
                text = " | ",
                color = PollPollTheme.colors.gray_400,
                style = PollPollTheme.typography.body03
            )
            HitsText(lastPost.watcherCount)
        }
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            text = "${lastPost.title}",
            color = PollPollTheme.colors.gray_900,
            style = PollPollTheme.typography.heading03
        )
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            text = "${lastPost.contents}",
            color = PollPollTheme.colors.gray_700,
            style = PollPollTheme.typography.body02
        )
    }


}

@Composable
fun TimeText(date: Date) {
    val sdf = SimpleDateFormat("MM.dd HH:mm")
    Text(
        text = sdf.format(date),
        color = PollPollTheme.colors.gray_400,
        style = PollPollTheme.typography.body03
    )
}

@Composable
fun HitsText(hits: Int) {
    Text(
        text = "조회수 $hits",
        color = PollPollTheme.colors.gray_400,
        style = PollPollTheme.typography.body03
    )
}

@Composable
fun VoteContent(
    lastPost: PostResponse?,
    voted: Boolean,
    vote: KFunction1<List<Int>, Unit>,
    reVote: () -> Unit,
    selectedIndex: Set<Int>,
    selectIndex: (Int) -> Unit,
    beforeVote: List<Vote>,
    afterVote: List<Vote>
) {
    if (lastPost == null) {
        Text(text = "loading...")
    } else {
        Column(
            modifier = Modifier
                .border(1.dp, color = PollPollTheme.colors.gray_200)
                .padding(horizontal = 20.dp, vertical = 30.dp)
                .fillMaxWidth()
        ) {
            ParticipantsText(lastPost.participantCount)
            Spacer(modifier = Modifier.size(15.dp))
            Text(
                text = "${lastPost.title}".replace(" ", "\u00A0"),
                color = PollPollTheme.colors.gray_700,
                style = PollPollTheme.typography.heading04
            )
            VoteDueDateText(Date(lastPost.pollEndAt))
            Spacer(modifier = Modifier.size(30.dp))

            if (voted) {
                // 투표가 완료된 리스트
                VoteResults(afterVote)

                PollButton(onClick = {  // 뷰모델에 있는 선택된것들로 최종 선택
                    reVote()
                }, enabled = selectedIndex.isNotEmpty(), modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "다시 투표하기",
                        color = Color.White,
                        style = PollPollTheme.typography.body03
                    )
                }

            } else {
                // 투표가 완료되지 않은 리스트
                // 클릭 가능한 아이템들
                // 아이템 클릭한걸 뷰모델로 넘기고, 선택 혹은 이미 선택된거면 해제
                for (beforeItem in beforeVote) {
                    VoteItem(beforeItem, selectIndex)
                    Spacer(modifier = Modifier.size(10.dp))
                }

                PollButton(onClick = {  // 뷰모델에 있는 선택된것들로 최종 선택
                    vote(selectedIndex.toList())
                }, enabled = selectedIndex.isNotEmpty(), modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "폴폴 참여하기",
                        color = Color.White,
                        style = PollPollTheme.typography.body03
                    )
                }
            }


        }
    }

}


@Composable
fun ParticipantsText(participants: Int) {
    Text(
        text = "총 ${participants}명 참여",
        color = PollPollTheme.colors.gray_400,
        style = PollPollTheme.typography.body04
    )
    Spacer(modifier = Modifier.size(15.dp))
}

@Composable
fun VoteDueDateText(date: Date) {
    val sdf = SimpleDateFormat("투표기간 : yyyy.MM.dd HH시 까지")
    Text(
        text = sdf.format(date),
        color = PollPollTheme.colors.gray_400,
        style = PollPollTheme.typography.body03
    )
}


@Composable
fun VoteResults(items: List<Vote>) {
    for ((index, item) in items.withIndex()) {
        VoteResultItem(item.copy(index = index, onClick = {

        }))
        Spacer(modifier = Modifier.size(10.dp))
    }
}

@Composable
fun VoteResultItem(vote: Vote) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable {

        }
    ){
        val backgroundModifier =
            if (vote.isSelected) Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(PollPollTheme.colors.primary_100)
                .border(
                    width = 1.dp,
                    color = PollPollTheme.colors.primary_500,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(10.dp)
            else Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(PollPollTheme.colors.gray_100)
                .padding(10.dp)
        Row(
            modifier = backgroundModifier
        ){
            val color = PollPollTheme.colors.gray_700
            Text(text = vote.text, style = PollPollTheme.typography.body03, color = color)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "${(vote.percent * 100f).roundToInt()}% (${vote.voteCount})", style = PollPollTheme.typography.body03, color = color)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .drawWithContent {
                    clipRect(right = size.width * vote.percent) {
                        this@drawWithContent.drawContent()
                    }
                }
                .background(PollPollTheme.colors.primary_500)
                .padding(10.dp)
        ){
            val color = Color.White
            Text(text = vote.text, style = PollPollTheme.typography.body03, color = color)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "${(vote.percent * 100f).roundToInt()}% (${vote.voteCount})", style = PollPollTheme.typography.body03, color = color)
        }
    }

}

@Composable
fun VoteItem(vote: Vote, select: (Int)-> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            select(vote.index)
        }
    ){
        val backgroundModifier =
            if (vote.isSelected) Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(PollPollTheme.colors.primary_100)
                .border(
                    width = 1.dp,
                    color = PollPollTheme.colors.primary_500,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(10.dp)
            else Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(PollPollTheme.colors.gray_100)
                .padding(10.dp)
        Row(
            modifier = backgroundModifier
        ){
            val color =
                if (vote.isSelected) PollPollTheme.colors.primary_500
                else PollPollTheme.colors.gray_900
            Text(text = vote.text, style = PollPollTheme.typography.body03, color = color)
        }
    }

}

@Preview
@Composable
fun ReadVoteScreenPreview() {
    PollPollTheme {
        ReadVoteScreen(postId = 0)
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun ReadVoteScreenScreenPortraitPreview() {
    PollPollTheme() {
        ReadVoteScreen(postId = 0)
    }
}