package com.ddd.pollpoll.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddd.pollpoll.designsystem.core.textfield.PollCoreTextField
import com.ddd.pollpoll.designsystem.core.textfield.TextFieldType
import com.ddd.pollpoll.designsystem.theme.PollPollTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PollOutLineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeHolderText: String,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    textFieldColors: TextFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = PollPollTheme.colors.gray_050,
        unfocusedContainerColor = PollPollTheme.colors.gray_050,
        disabledContainerColor = PollPollTheme.colors.gray_050,
    )
) {
    PollCoreTextField(
        value = value,
        textFieldColors = textFieldColors,
        onValueChange = onValueChange,
        modifier = modifier,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,

        placeholder = {
            Text(
                text = placeHolderText,
                color = PollPollTheme.colors.gray_300,
                style = PollPollTheme.typography.body02,
            )
        },
        trailingIcon = trailingIcon,
        textStyle = PollPollTheme.typography.heading05,
        paddingValues = PaddingValues(horizontal = 15.dp, vertical = 12.dp),
        textFiledType = TextFieldType.Outlined,
    )
}

/**
 * Insert에 사용되는 글자 제한이 있는 TextField입니다.
 *
 * @param text
 * @param placeholderText
 * @param onValueChange
 * @param textStyle
 * @param contextPadding
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PollInputTextField(
    text: String,
    placeholderText: String,
    onValueChange: (String) -> Unit = {},
    maxLength: Int? = null,
    textStyle: TextStyle = PollPollTheme.typography.heading05,
    contextPadding: PaddingValues = PaddingValues(top = 6.dp, bottom = 20.dp),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    var focusState by remember { mutableStateOf(false) }
    Column() {
        PollCoreTextField(
            value = text,
            placeholder = { Text(text = placeholderText) },
            textStyle = textStyle,
            onValueChange = { textValue ->
                maxLength?.let { maxLength ->
                    if (textValue.length <= maxLength) onValueChange(textValue)
                } ?: onValueChange(textValue)
            },
            paddingValues = contextPadding,
            textFieldColors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                unfocusedIndicatorColor = PollPollTheme.colors.gray_300,
                focusedIndicatorColor = PollPollTheme.colors.gray_900,
            ),
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            focusedChanged = { focusState = it.isFocused },
        )
        maxLength?.let { max ->

            Text(
                modifier = Modifier.align(Alignment.End),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = if (text.isNotEmpty()) PollPollTheme.colors.gray_900 else PollPollTheme.colors.gray_400)) {
                        append("${text.length}자 ")
                    }
                    append("/ ${max}자")
                },
                color = PollPollTheme.colors.gray_400,
                style = PollPollTheme.typography.body04,
            )
        }
    }
}

@Preview
@Composable
fun PollTextFieldPreview() {
    PollPollTheme() {
        var text by remember { mutableStateOf("냐옹맨 ") }
        Column() {
            PollInputTextField(
                text,
                onValueChange = { text = it },
                placeholderText = "제목을 입력해주세요",
                maxLength = 50,
            )

            PollInputTextField(
                text,
                onValueChange = { text = it },
                placeholderText = "제목을 입력해주세요",
                maxLength = 50,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun GeneralTextFieldPreview() {
    PollPollTheme() {
        var text by remember { mutableStateOf("") }

        TextField(text, onValueChange = { text = it })
    }
}
