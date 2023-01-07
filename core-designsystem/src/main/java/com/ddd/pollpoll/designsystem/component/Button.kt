package com.ddd.pollpoll.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddd.pollpoll.designsystem.icon.PollIcon
import com.ddd.pollpoll.designsystem.theme.PollPollTheme

@Composable
fun PollLoginButton(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(horizontal = 88.dp, 14.dp),
    @DrawableRes iconRes: Int = PollIcon.Google,
    text: String = "구글 ID 로그인"
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = PollPollTheme.colors.gray_700,
            containerColor = Color.White
        ),
        border = BorderStroke(
            width = PollButtonDefaults.OutlinedButtonBorderWidth,
            color = if (enabled) {
                PollPollTheme.colors.gray_200
            } else {
                MaterialTheme.colorScheme.onSurface.copy(
                    alpha = PollButtonDefaults.DisabledOutlinedButtonBorderAlpha
                )
            }
        ),
        contentPadding = contentPadding,
        content = {
            Image(
                imageVector = ImageVector.vectorResource(id = iconRes),
                contentDescription = ""
            )
            Spacer(modifier = modifier.width(10.dp))
            Text(text = text, style = PollPollTheme.typography.body02)
        }

    )
}

@@Composable
fun PollButton() {
    Button(shape = RoundedCornerShape(0.dp), onClick = { /*TODO*/ }) {

    }
}

object PollButtonDefaults {
    // TODO: File bug
    // OutlinedButton border color doesn't respect disabled state by default
    const val DisabledOutlinedButtonBorderAlpha = 0.12f

    // TODO: File bug
    // OutlinedButton default border width isn't exposed via ButtonDefaults
    val OutlinedButtonBorderWidth = 1.dp
}



@Preview
@Composable
fun PollButtonPreview() {
    PollPollTheme {
        PollButton()
    }
}
