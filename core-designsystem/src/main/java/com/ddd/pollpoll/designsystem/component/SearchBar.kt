package com.ddd.pollpoll.designsystem.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddd.pollpoll.designsystem.core.textfield.PollCoreTextField
import com.ddd.pollpoll.designsystem.core.textfield.TextFieldType
import com.ddd.pollpoll.designsystem.icon.PollIcon
import com.ddd.pollpoll.designsystem.theme.PollPollTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PollSearchBar(value: String, onValueChange: (String) -> Unit) {
    PollCoreTextField(
        value = value,
        textFieldColors = TextFieldDefaults.textFieldColors(
            containerColor = PollPollTheme.colors.gray_050,
        ),
        onValueChange = onValueChange,
        modifier = Modifier,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ),

        placeholder = {
            Text(
                text = "궁금한 투표를 검색해보세요",
                color = PollPollTheme.colors.gray_300,
                style = PollPollTheme.typography.body02,
            )
        },
        trailingIcon = {
            Row() {
                if (value.isNotEmpty()) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = PollIcon.CloseButton),
                            contentDescription = "",
                        )
                    }
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = PollIcon.Search),
                        contentDescription = "",
                        tint = PollPollTheme.colors.primary_500,
                    )
                }
            }
        },
        textStyle = PollPollTheme.typography.heading05,
        paddingValues = PaddingValues(horizontal = 15.dp, vertical = 12.dp),
        textFiledType = TextFieldType.Outlined,
    )
}

@Preview
@Composable
fun PollSearchBarPreview() {
    var text by remember { mutableStateOf("") }
    PollPollTheme {
        PollSearchBar(text, { text = it })
    }
}
