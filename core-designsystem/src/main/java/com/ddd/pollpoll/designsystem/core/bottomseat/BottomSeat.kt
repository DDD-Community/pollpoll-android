package com.ddd.pollpoll.designsystem.core.bottomseat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddd.pollpoll.designsystem.theme.PollPollTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PollModalBottomSheetLayout(
    sheetContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    sheetState: ModalBottomSheetState,
    sheetShape: Shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
    content: @Composable () -> Unit
) {
    ModalBottomSheetLayout(
        sheetContent = sheetContent,
        modifier = modifier,
        sheetState = sheetState,
        sheetShape = sheetShape
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun PollBottomSeatPreview() {
}
