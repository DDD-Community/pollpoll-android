package com.ddd.pollpoll.feature.vote

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.ddd.pollpoll.designsystem.component.PollLabel
import com.ddd.pollpoll.designsystem.component.PollProgressBar
import com.ddd.pollpoll.designsystem.component.PollTopBar
import com.ddd.pollpoll.designsystem.icon.PollIcon
import com.ddd.pollpoll.designsystem.theme.PollPollTheme
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadVoteScreen(
    viewModel: ReadVoteViewModel = hiltViewModel()
) {
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
        val scrollState = rememberScrollState()
        Surface(modifier = Modifier.padding(it)) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                VoteInfo()
                Spacer(modifier = Modifier.size(20.dp))


                VoteContent(viewModel.votes, viewModel.voted.value, viewModel::voteSelectedVote, viewModel::reVote)
            }
        }
    }
}

@Composable
fun VoteInfo() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        PollLabel("초이스")
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "고민",
            color = PollPollTheme.colors.gray_900,
            style = PollPollTheme.typography.body04
        )
    }
    Spacer(modifier = Modifier.size(12.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .background(Color(0xFFA5A5A5)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "n",
                color = Color.Black,
                style = PollPollTheme.typography.body03
            )
        }
        Spacer(modifier = Modifier.size(6.dp))
        Text(
            text = "nickname",
            color = PollPollTheme.colors.gray_900,
            style = PollPollTheme.typography.body03
        )
    }
    Spacer(modifier = Modifier.size(5.dp))
    Row() {
        TimeText(Date())
        Text(
            text = " | ",
            color = PollPollTheme.colors.gray_400,
            style = PollPollTheme.typography.body03
        )
        HitsText(66)
    }
    Spacer(modifier = Modifier.size(20.dp))
    Text(
        text = "개발자분들 사용하시는 모니터 해상도가 어떻게 되시나요?",
        color = PollPollTheme.colors.gray_900,
        style = PollPollTheme.typography.heading03
    )
    Spacer(modifier = Modifier.size(20.dp))
    Text(
        text = "이번에 회사에 맥북에 연결 할 듀얼 모니터를 교체하려고 하는데 개발자에게 고사양의 모니터가 필요할 지 의문이 들어서 투표 올려봅니다! 해상도 좋은 모니터 쓰면 장점이 있나요??",
        color = PollPollTheme.colors.gray_700,
        style = PollPollTheme.typography.body02
    )
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
fun VoteContent(votes: List<Vote>, voted: Boolean, voteSelectedVote: () -> Unit , reVote: () -> Unit ) {
    Column(
        modifier = Modifier
            .border(1.dp, color = PollPollTheme.colors.gray_200)
            .padding(horizontal = 20.dp, vertical = 30.dp)
            .fillMaxWidth()
    ) {
        ParticipantsText(194)
        Spacer(modifier = Modifier.size(15.dp))
        Text(
            text = "개발자분들 사용하시는 모니터 해상도가 어떻게 되시나요?".replace(" ", "\u00A0"),
            color = PollPollTheme.colors.gray_700,
            style = PollPollTheme.typography.heading04
        )
        VoteDueDateText(Date())
        Spacer(modifier = Modifier.size(30.dp))
        if (voted) {
            VoteResults(votes)

            Button(onClick = { reVote() }, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "다시 투표하기",
                    color = Color.White,
                    style = PollPollTheme.typography.body03
                )
            }
        } else {
            VoteItemSelector(votes)

            var isLessOneVoteSelected = false
            for (vote in votes) {
                if (vote.isSelected) isLessOneVoteSelected = true
            }
            Button(onClick = { voteSelectedVote() }, enabled = isLessOneVoteSelected, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "폴폴 참여하기",
                    color = Color.White,
                    style = PollPollTheme.typography.body03
                )
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

data class Vote(
    val index: Int,
    val text: String,
    val percent: Float,
    val voteCount: Int,
    val isSelected: Boolean,
    val onClick: () -> Unit
)

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
fun VoteItemSelector(items: List<Vote>) {
    for ((index, item) in items.withIndex()) {
        VoteItem(item.copy(index = index))
        Spacer(modifier = Modifier.size(10.dp))
    }
}

@Composable
fun VoteItem(vote: Vote) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            vote.onClick()
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
        ReadVoteScreen()
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun ReadVoteScreenScreenPortraitPreview() {
    PollPollTheme() {
        ReadVoteScreen()
    }
}