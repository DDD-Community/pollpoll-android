package com.ddd.pollpoll.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ddd.pollpoll.designsystem.component.PollLabel
import com.ddd.pollpoll.designsystem.component.PollProgressBar
import com.ddd.pollpoll.designsystem.icon.PollIcon
import com.ddd.pollpoll.designsystem.theme.PollPollTheme
import com.ddd.pollpoll.feature.main.model.PostUi
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.absoluteValue

fun LazyListScope.PollCardLazyList(
    posts: List<PostUi>,
    navigateToReadVote: (Int) -> Unit,
) {
    items(
        items = posts,
        key = { it.postId },
    ) { post ->
        PollCard(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            post = post,
            onClick = { navigateToReadVote(post.postId) },
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PollCard(
    modifier: Modifier = Modifier,
    expireDate: Date = Date(),
    participantsCount: Int = 0,
    onClick: () -> Unit = {},
    post: PostUi,
) {
    var currentTime by remember { mutableStateOf(System.currentTimeMillis()) }
    val timeDiff = post.pollEndAt.endDate - currentTime
    val isPollEnd = (timeDiff) < 0
    val timeProgress =
        timeDiff.absoluteValue.toFloat() / (post.pollEndAt.endDate - post.postCreatedAt).toFloat()

    //시간이 지났을때, 리컴포지션 방지
    LaunchedEffect(key1 = Unit) {
        while (!isPollEnd) {
            delay(1000)
            currentTime = System.currentTimeMillis()
        }
    }

    Card(
        modifier = modifier.semantics {
            onClick(label = "투표를 하거나 투표보기", action = { true })
        },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        onClick = { onClick() },
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                PollLabel("초이스")
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "${post.categoryName}",
                    color = PollPollTheme.colors.gray_900,
                    style = PollPollTheme.typography.body04,
                )
            }
            Spacer(modifier = Modifier.size(15.dp))

            Text(
                text = "${post.title}",
                color = PollPollTheme.colors.gray_900,
                style = PollPollTheme.typography.heading05,
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "${post.contents}",
                color = PollPollTheme.colors.gray_700,
                style = PollPollTheme.typography.heading05,
                fontWeight = FontWeight.Normal,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.size(25.dp))

            val iconColor = if (isPollEnd) PollPollTheme.colors.gray_300 else Color(0xff8477F8)

            Row() {
                Icon(
                    painter = painterResource(id = PollIcon.Alarm),
                    contentDescription = "",
                    tint = iconColor,
                )
                Spacer(modifier = Modifier.size(3.dp))
                TimeText(post.pollEndAt.endDate, currentTime)
            }
            Spacer(modifier = Modifier.size(8.dp))

            val timeProgress =

                if (isPollEnd) {
                    PollProgressBar(progress = 0f)
                } else {
                    PollProgressBar(timeProgress)
                }

            Spacer(modifier = Modifier.size(32.dp))
            Button(
                onClick = { onClick() },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = PollPollTheme.colors.gray_050),
            ) {
                if (isPollEnd) {
                    Text(
                        text = "투표 결과 볼래요",
                        color = PollPollTheme.colors.gray_700,
                        style = PollPollTheme.typography.body03,
                    )
                } else {
                    Text(
                        text = "지금 ",
                        color = PollPollTheme.colors.gray_700,
                        style = PollPollTheme.typography.body03,
                    )
                    Text(
                        text = "${post.participantCount}명",
                        color = PollPollTheme.colors.primary_500,
                        style = PollPollTheme.typography.body03,
                    )
                    Text(
                        text = " 참여 중이에요",
                        color = PollPollTheme.colors.gray_700,
                        style = PollPollTheme.typography.body03,
                    )
                }
            }
        }
    }
}

@Composable
fun TimeText(expireDateTime: Long, currentTime: Long) {
    val diff = expireDateTime - currentTime
    val diffAbsoluteValue = diff.absoluteValue
    val sdf = SimpleDateFormat("MM.dd HH:mm")

    val diffSeconds: Long = (diffAbsoluteValue / 1000) % 60
    val diffMinutes: Long = (diffAbsoluteValue / (60 * 1000)) % 60
    val diffHours: Long = (diffAbsoluteValue / (60 * 60 * 1000)) % 24
    val diffDays: Long = diffAbsoluteValue / (24 * 60 * 60 * 1000)

    val hoursString = if (diffHours < 10) "0$diffHours" else "$diffHours"
    val minuteString = if (diffMinutes < 10) "0$diffMinutes" else diffMinutes
    val secondsString = if (diffSeconds < 10) "0$diffSeconds" else diffSeconds

    if (diff < 0) {
        Text(
            text = "종료된 투표",
            color = PollPollTheme.colors.gray_300,
            style = PollPollTheme.typography.body03,
        )
    } else {
        Text(
            text = "${diffDays}일 $hoursString:$minuteString:$secondsString 남았어요!",
            color = PollPollTheme.colors.gray_400,
            style = PollPollTheme.typography.body03,
        )
    }
}
// @Composable
// fun TimeProgressText(expireDate: Date) {
//    // date gap millisceond to date
//    val expireDateTime = expireDate.time
//    var currentTime by remember { mutableStateOf(System.currentTimeMillis()) }
//
//    LaunchedEffect(key1 = Unit) {
//        while (true) {
//            delay(1000)
//            currentTime = System.currentTimeMillis()
//        }
//    }
//
//    val diff = (expireDateTime - currentTime).absoluteValue
//    val sdf = SimpleDateFormat("MM.dd HH:mm")
//
//    val diffSeconds: Long = (diff / 1000) % 60
//    val diffMinutes: Long = (diff / (60 * 1000)) % 60
//    val diffHours: Long = (diff / (60 * 60 * 1000)) % 24
//    val diffDays: Long = diff / (24 * 60 * 60 * 1000)
//
//    val hoursString = if (diffHours < 10) "0$diffHours" else "$diffHours"
//    val minuteString = if (diffMinutes < 10) "0$diffMinutes" else diffMinutes
//    val secondsString = if (diffSeconds < 10) "0$diffSeconds" else diffSeconds
//
//    Text(
//        text = "${diffDays}일 $hoursString:$minuteString:$secondsString 남았어요!",
//        color = PollPollTheme.colors.gray_400,
//        style = PollPollTheme.typography.body03,
//    )
// }
