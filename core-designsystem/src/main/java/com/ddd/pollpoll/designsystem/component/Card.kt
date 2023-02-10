package com.ddd.pollpoll.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddd.pollpoll.designsystem.icon.PollIcon
import com.ddd.pollpoll.designsystem.theme.PollPollTheme

@Composable
fun PollTrendCard(
    type: PollCardType = PollCardType.AB
) {
    Card(shape = RoundedCornerShape(20.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp, 20.dp))
                .background(color = if (type == PollCardType.AB) PollPollTheme.colors.secondary_500 else PollPollTheme.colors.primary_500)
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(PollPollTheme.spaces.large))
            Text(text = if(type== PollCardType.AB) "A / B" else "초이스", style = PollPollTheme.typography.body04, color = Color.White)
            Spacer(modifier = Modifier.weight(1f))
            Icon(painterResource(id = PollIcon.Alarm), contentDescription = "")
            Spacer(modifier = Modifier.width(5.dp))
            Text("2일 13:32:31", style = PollPollTheme.typography.body04, color = Color.White)
            Spacer(modifier = Modifier.width(PollPollTheme.spaces.large))
        }
        Column(
            Modifier.padding(horizontal = 30.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "사무실에서 손톱 깎는거 잘못이다/아니다", style = PollPollTheme.typography.heading05)
            Spacer(modifier = Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = PollIcon.Fire),
                    tint = PollPollTheme.colors.secondary_300,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = "256,834명 참여",
                    style = PollPollTheme.typography.body03,
                    color = PollPollTheme.colors.gray_500
                )
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    painter = painterResource(id = PollIcon.Cloud),
                    tint = PollPollTheme.colors.secondary_300,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = "22593명이 구경해요",
                    style = PollPollTheme.typography.body03,
                    color = PollPollTheme.colors.gray_500
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            PollButton(
                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                shape = RoundedCornerShape(20.dp),
                color = if (type == PollCardType.AB) PollButtonColor.BLUE else PollButtonColor.ORANGE
            ) {
                Text(
                    modifier = Modifier,
                    text = "참여하기",
                    style = PollPollTheme.typography.heading05,
                    color = PollPollTheme.colors.gray_050
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

enum class PollCardType {
    AB, CHOICE
}

@Preview
@Composable
fun PollTendCardPreview() {
    PollTrendCard()
}
