package com.ddd.pollpoll.designsystem.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddd.pollpoll.core.ui.textfield.PollCoreTextField
import com.ddd.pollpoll.designsystem.theme.PollPollTheme

/**
 * 언제든 최적화 당할수 있음에 유의하며 사용할것!
 *
 * @param text
 * @param placeholderText
 * @param onValueChange
 * @param textStyle
 * @param contextPadding
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PollTextField(
    text: String,
    placeholderText: String,
    onValueChange: (String) -> Unit = {},
    textStyle: TextStyle = PollPollTheme.typography.heading05,
    contextPadding: PaddingValues = PaddingValues(horizontal = 0.dp, 6.dp)

) {
    PollCoreTextField(
        text = text,
        placeholderText = placeholderText,
        textStyle = textStyle,
        onValueChange = onValueChange,
        paddingValues = contextPadding,
        textFieldColors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            unfocusedIndicatorColor = PollPollTheme.colors.gray_300,
            focusedIndicatorColor = PollPollTheme.colors.gray_900
        )
    )
}

// colors = TextFieldDefaults.textFieldColors(
// containerColor = Color.White,
// focusedIndicatorColor = PollPollTheme.colors.gray_900
// ),
//
// textStyle = PollPollTheme.typography.heading05,

// @Immutable
// data class PollColors(
//    val containerColor: Color,
//    val textColor: Color,
//    val focusedColor: Color
// ){
//    fun PollColorsDefault(){
//        PollColors(
//            containerColor = Color.White,
//            textColor
//        )
//    }
// }

@Preview
@Composable
fun PollTextFieldPreview() {
    PollPollTheme() {
        PollTextField("개발자분들 사용하시는 모니터 해상도가 어떻게 되시나요??", placeholderText = "제목을 입력해주세요")
    }
}
