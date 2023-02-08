package com.ddd.pollpoll.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddd.pollpoll.core.CustomAlertDialog
import com.ddd.pollpoll.designsystem.theme.PollPollTheme

@Composable
fun PollAlertDialog(
    onDismissRequest: () -> Unit,
    onCancelClicked: () -> Unit,
    onConfirmClicked: () -> Unit
) {
    CustomAlertDialog(onDismissRequest = onDismissRequest) {
        Box(modifier = Modifier.fillMaxSize()) {
            Surface(modifier = Modifier.size(266.dp, 170.dp).align(Alignment.Center), color = Color.White) {
                Column(Modifier.padding(horizontal = 20.dp)) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "등록을 취소할까요?",
                        style = PollPollTheme.typography.body02,
                        color = PollPollTheme.colors.gray_700
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "지금까지 작성한 건 삭제가 됩니다.",
                        style = PollPollTheme.typography.body04,
                        color = PollPollTheme.colors.gray_700
                    )
                    Spacer(modifier = Modifier.height(55.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = onCancelClicked) {
                            Text(
                                text = "취소",
                                style = PollPollTheme.typography.body03,
                                color = PollPollTheme.colors.gray_400
                            )
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                        TextButton(onClick = onConfirmClicked) {
                            Text(
                                text = "확인",
                                style = PollPollTheme.typography.body03,
                                color = PollPollTheme.colors.primary_500
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PollAlertDialogPreview() {
    PollAlertDialog(onDismissRequest = {}, onCancelClicked = {}, onConfirmClicked = {})
}
