package com.ddd.pollpoll.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.ddd.pollpoll.designsystem.icon.PollIcon
import com.ddd.pollpoll.designsystem.theme.PollPollTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PollSearchBar(
    value: String,
    placeHolderText: String = "궁금한 투표를 검색해보세요",
    onValueChange: (String) -> Unit,
    onCloseButtonClicked: () -> Unit = {},
    onSearchButtonClicked: () -> Unit = {},
) {
    PollOutLineTextField(
        value,
        onValueChange,
        placeHolderText = placeHolderText,
        keyboardActions = KeyboardActions(
            onSearch = { onSearchButtonClicked() },
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ),
        trailingIcon = {
            Row() {
                if (value.isNotEmpty()) {
                    IconButton(onClick = onCloseButtonClicked) {
                        Icon(
                            painter = painterResource(id = PollIcon.CloseButton),
                            contentDescription = "",
                        )
                    }
                }
                IconButton(onClick = onSearchButtonClicked) {
                    Icon(
                        painter = painterResource(id = PollIcon.Search),
                        contentDescription = "",
                        tint = PollPollTheme.colors.primary_500,
                    )
                }
            }
        },
    )
}

@Preview
@Composable
fun PollSearchBarPreview() {
    var text by remember { mutableStateOf("") }
    PollPollTheme {
        PollSearchBar(text, "궁금한 투표를 검색해보세요", {})
    }
}
