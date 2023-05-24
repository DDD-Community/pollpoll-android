package com.ddd.pollpoll.designsystem.core.bottomseat

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PollModalBottomSheetLayout(
    sheetContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    sheetState: BottomSheetScaffoldState,
    sheetShape: Shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
    content: @Composable () -> Unit,
) {
    BottomSheetScaffold(
        sheetContent = sheetContent,
        modifier = modifier,
        scaffoldState = sheetState,
        sheetShape = sheetShape,
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun PollBottomSeatPreview() {
}
