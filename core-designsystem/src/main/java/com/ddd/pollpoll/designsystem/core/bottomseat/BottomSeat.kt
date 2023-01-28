package com.ddd.pollpoll.designsystem.core.bottomseat

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PollBottomSeat(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    textStyle: TextStyle,
    textFieldColors: TextFieldColors = TextFieldDefaults.textFieldColors(),
    paddingValues: PaddingValues,
    keyboardActions: KeyboardActions,
    maxLength: Int = 0
) {
    com.ddd.pollpoll.designsystem.core.textfield.PollCoreTextField(
        colors = textFieldColors,
        textStyle = textStyle,
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = placeholderText) },
        contentPadding = paddingValues,
        keyboardActions = keyboardActions
    )
}