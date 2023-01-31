package com.ddd.pollpoll.designsystem.core.textfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.ddd.pollpoll.designsystem.theme.Gray900

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PollCoreTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    textStyle: TextStyle,
    textFieldColors: TextFieldColors = TextFieldDefaults.textFieldColors(),
    paddingValues: PaddingValues,
    keyboardActions: KeyboardActions,
    maxLength: Int = 0,
    focusedChanged: (FocusState) -> Unit = {}
) {
    PollCoreTextField(
        colors = textFieldColors,
        textStyle = textStyle,
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = placeholderText) },
        contentPadding = paddingValues,
        focusedChanged = focusedChanged,
        keyboardActions = keyboardActions
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PollCoreTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    focusedChanged: (FocusState) -> Unit = {},
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.filledShape,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(),
    selectionColors: TextSelectionColors = TextSelectionColors(Color.Black, Color.White),
    contentPadding: PaddingValues
) {
    val textColor = textStyle.color.takeOrElse {
        rememberUpdatedState(if (enabled) Gray900 else Color.White).value
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    CompositionLocalProvider(LocalTextSelectionColors provides selectionColors) {
        @OptIn(ExperimentalMaterial3Api::class)
        BasicTextField(
            value = value,
            modifier = modifier
                .width(320.dp)
                .defaultMinSize(
                    minWidth = 320.dp,
                    minHeight = 0.dp
                )
                .onFocusChanged(focusedChanged),
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = mergedTextStyle,
            cursorBrush = SolidColor(cursorColor(isError, Color.White, Color.Black).value),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            decorationBox =
            @Composable { innerTextField ->

                TextFieldDecorationBox(
                    value = value,
                    visualTransformation = visualTransformation,
                    innerTextField = innerTextField,
                    placeholder = placeholder,
                    label = label,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    supportingText = supportingText,
                    shape = shape,
                    singleLine = singleLine,
                    enabled = enabled,
                    isError = isError,
                    interactionSource = interactionSource,
                    colors = colors,
                    contentPadding = contentPadding
                )
            }
        )
    }
}

@Composable
internal fun cursorColor(
    isError: Boolean,
    errorCursorColor: Color,
    cursorColor: Color
): State<Color> {
    return rememberUpdatedState(if (isError) errorCursorColor else cursorColor)
}
