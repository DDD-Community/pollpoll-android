package com.ddd.pollpoll.designsystem.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ddd.pollpoll.designsystem.core.radioButton.CoreRadioButton

@Composable
fun PollRadioButton(selected: Boolean = false, onClick: (() -> Unit)? = null) {
    CoreRadioButton(selected = selected, onClick = onClick)
}

@Preview(showBackground = true)
@Composable
fun PollRadioButtonPreview() {
    PollRadioButton()
}
