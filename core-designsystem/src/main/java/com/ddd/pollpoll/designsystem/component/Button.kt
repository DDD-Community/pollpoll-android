package com.ddd.pollpoll.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
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

@Composable
fun PollButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(0.dp),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    color: PollButtonColor = PollButtonColor.ORANGE,
    content: @Composable RowScope.() -> Unit = {}
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (color == PollButtonColor.BLUE) PollPollTheme.colors.secondary_500 else PollPollTheme.colors.primary_500,
            disabledContainerColor = PollPollTheme.colors.gray_300
        ),
        enabled = enabled,
        shape = shape,
        onClick = onClick
    ) {
        content()
    }
}
@Composable
fun PollRoundedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(0.dp),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    color: PollButtonColor = PollButtonColor.ORANGE,
    content: @Composable RowScope.() -> Unit = {}
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (color == PollButtonColor.BLUE) PollPollTheme.colors.secondary_500 else PollPollTheme.colors.primary_500,
            disabledContainerColor = PollPollTheme.colors.gray_300
        ),
        enabled = enabled,
        shape = shape,
        onClick = onClick
    ) {
        content()
    }
}

@Composable
fun PollCategoryButton(
    onClick: () -> Unit = {},
    clicked: Boolean = false,
    imageUrl: String = "",
    text: String = "전체"
) {
    Column() {
        Button(
            onClick = onClick,
            modifier = Modifier.size(60.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (clicked) PollPollTheme.colors.primary_500 else Color(0xFFF5F5F5)
            ),
            elevation = ButtonDefaults.buttonElevation(2.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            AsyncImage(
                modifier = Modifier.align(CenterVertically).size(36.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .decoderFactory(SvgDecoder.Factory())
//                    .size(size = 40)
                    .build(),
                placeholder = painterResource(PollIcon.Carrier),
                contentDescription = ""
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier.align(CenterHorizontally),
            text = text,
            color = if (clicked) PollPollTheme.colors.primary_500 else PollPollTheme.colors.gray_900,
            style = PollPollTheme.typography.heading05
        )
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

enum class PollButtonColor {
    ORANGE, BLUE
}

@Preview
@Composable
fun PollButtonPreview() {
    PollPollTheme {
        PollButton()
    }
}

@Preview
@Composable
fun PollCategoryButtonPreview() {
    PollPollTheme {
        PollCategoryButton()
    }
}

@Preview
@Composable
fun PollCategoryButtonEnabledPreview() {
    PollPollTheme {
        PollCategoryButton(clicked = false)
    }
}
