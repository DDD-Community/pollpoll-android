package com.ddd.pollpoll.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddd.pollpoll.designsystem.icon.PollIcon
import com.ddd.pollpoll.designsystem.theme.PollPollTheme

@Composable
fun PollIconButton(
    @DrawableRes iconRes: Int = PollIcon.Love,
    contentDes: String? = null,
    isClicked: Boolean = false,
    onClick: () -> Unit ={}
) {
    OutlinedIconButton(
        onClick = { onClick },
        colors = IconButtonDefaults.outlinedIconButtonColors(
            containerColor = if (isClicked) PollPollTheme.colors.primary_100 else Color.White

        ),

        border = BorderStroke(
            width = 1.dp,
            color = if (isClicked) Color.White else PollPollTheme.colors.primary_050
        )

    ) {
        Image(painter = painterResource(id = iconRes), contentDescription = contentDes)
    }
}

@Preview
@Composable
fun PollIconButtonPreview() {
    PollPollTheme() {
        PollIconButton()
    }
}
