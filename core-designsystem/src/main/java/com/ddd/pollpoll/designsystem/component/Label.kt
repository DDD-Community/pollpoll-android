package com.ddd.pollpoll.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddd.pollpoll.designsystem.theme.*
import com.ddd.pollpoll.designsystem.theme.Primary500
import com.ddd.pollpoll.designsystem.theme.Secondary500

@Composable
fun PollLabel(
    text: String,
    color: LabelColor = LabelColor.ORANGE
) {
    Surface(
        color = if (color == LabelColor.ORANGE) Primary500 else Secondary500,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp, 3.5.dp)
                .defaultMinSize(minWidth = 35.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                color = Gray050,
                style = PollPollTheme.typography.body04
            )
        }
    }
}

@Preview
@Composable
fun PollLabelPreview() {
    PollPollTheme() {
        Column() {
            PollLabel("초이스")
            PollLabel(color = LabelColor.BLUE, text = "A / B")
        }
    }
}

enum class LabelColor {
    ORANGE, BLUE
}
