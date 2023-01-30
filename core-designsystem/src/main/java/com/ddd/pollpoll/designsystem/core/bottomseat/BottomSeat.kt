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

    // / TODO: 어떻게 개선하면 좋을까?
//    val context = LocalContext.current
//    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black.copy(alpha = 0.5f)) {
//        BoxWithConstraints() {
//            val minHeight = with(Density(context)) { minHeight.toPx() }
//            val yTranslation: Float by animateFloatAsState(if (isVisible) 0f else minHeight)
//            Surface(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .wrapContentHeight()
//                    .graphicsLayer(translationY = yTranslation)
//                    .align(Alignment.BottomCenter),
//                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
//                color = Color.White
//            ) {
//                Column() {
//                    Spacer(modifier = Modifier.height(20.dp))
//                    content()
//                }
//            }
//        }
//    }
}

@Preview(showBackground = true)
@Composable
fun PollBottomSeatPreview() {
}
