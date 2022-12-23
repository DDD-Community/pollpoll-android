package com.ddd.pollpoll.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PollOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
//    OutlinedButton(
//        onClick = onClick,
//        modifier = modifier,
//        enabled = enabled,
//        colors = ButtonDefaults.outlinedButtonColors(
//            contentColor = MaterialTheme.colorScheme.onBackground
//        ),
//        border = BorderStroke(
//            width = NiaButtonDefaults.OutlinedButtonBorderWidth,
//            color = if (enabled) {
//                MaterialTheme.colorScheme.outline
//            } else {
//                MaterialTheme.colorScheme.onSurface.copy(
//                    alpha = NiaButtonDefaults.DisabledOutlinedButtonBorderAlpha
//                )
//            }
//        ),
//        contentPadding = contentPadding,
//        content = content
//
//    )
}

object PollButtonDefaults {
    // TODO: File bug
    // OutlinedButton border color doesn't respect disabled state by default
    const val DisabledOutlinedButtonBorderAlpha = 0.12f

    // TODO: File bug
    // OutlinedButton default border width isn't exposed via ButtonDefaults
    val OutlinedButtonBorderWidth = 1.dp
}
