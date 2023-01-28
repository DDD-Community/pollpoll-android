package com.ddd.pollpoll.designsystem.component

import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ddd.pollpoll.designsystem.theme.PollPollTheme


@Composable
fun PollRadioButton() {
    RadioButton(selected = true, onClick = { /*TODO*/ }  , colors = RadioButtonDefaults.colors(
        selectedColor = PollPollTheme.colors.primary_500
    ))
}

@Preview(showBackground = true)
@Composable
fun PollRadioButtonPreview() {
    PollRadioButton()
}