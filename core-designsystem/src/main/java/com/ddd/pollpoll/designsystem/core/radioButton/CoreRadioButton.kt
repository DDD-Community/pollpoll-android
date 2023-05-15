package com.ddd.pollpoll.designsystem.core.radioButton

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddd.pollpoll.designsystem.theme.PollPollTheme

@Composable
fun CoreRadioButton(
    selected: Boolean,
    onClick: (() -> Unit)? = {},
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val dotRadius = animateDpAsState(
        targetValue = if (selected) RadioButtonDotSize else 0.dp,
        animationSpec = tween(durationMillis = RadioAnimationDuration), label = "",
    )

    val radioColor = radioColor(enabled, selected)
    val selectableModifier = if (onClick != null) {
        Modifier.selectable(
            selected = selected,
            onClick = onClick,
            enabled = enabled,
            role = Role.RadioButton,
            interactionSource = interactionSource,
            indication = rememberRipple(
                bounded = false,
                radius = RadioRadius,
            ),
        )
    } else {
        Modifier
    }

    Canvas(
        modifier.then(selectableModifier)
            .wrapContentSize(Alignment.Center)
            .padding(RadioButtonPadding)
            .requiredSize(10.dp),
    ) {
        // Draw the radio button
        val strokeWidth = RadioStrokeWidth.toPx()
        drawCircle(
            radioColor.value,
            radius = RadioRadius.toPx(),
            style = Stroke(strokeWidth),
        )
        if (dotRadius.value > 0.dp) {
            drawCircle(radioColor.value, dotRadius.value.toPx(), style = Fill)
        }
    }
}

@Composable
fun radioColor(enabled: Boolean, selected: Boolean): State<Color> {
    val target = when {
        enabled && selected -> PollPollTheme.colors.primary_500
        enabled && !selected -> disabledColor
        !enabled && selected -> Color.Black
        else -> Color.Black
    }

    // If not enabled 'snap' to the disabled state, as there should be no animations between
    // enabled / disabled.
    return if (enabled) {
        animateColorAsState(target, tween(durationMillis = RadioAnimationDuration))
    } else {
        rememberUpdatedState(target)
    }
}

// @Composable
// fun dotColor(enabled: Boolean, selected: Boolean): State<Color> {
//    val target = when {
//        enabled && selected -> Color.Cyan
//        enabled && !selected -> Color.Black
//        !enabled && selected -> Color.Black
//        else -> Color.Black
//    }
//
//    // If not enabled 'snap' to the disabled state, as there should be no animations between
//    // enabled / disabled.
//    return if (enabled) {
//        animateColorAsState(target, tween(durationMillis = RadioAnimationDuration))
//    } else {
//        rememberUpdatedState(target)
//    }
// }

@Preview(showBackground = true)
@Composable
fun CoreRadioButtonPreview() {
    CoreRadioButton(selected = true)
}

@Preview(showBackground = true)
@Composable
fun CoreRadioButtonDisabledPreview() {
    CoreRadioButton(selected = false)
}

private const val RadioAnimationDuration = 100
private val RadioRadius = 10.dp
private val RadioButtonPadding = 2.dp
private val RadioButtonDotSize = 5.dp
private val RadioStrokeWidth = 1.dp

private val disabledColor = Color(0xFF8A8A8A)
