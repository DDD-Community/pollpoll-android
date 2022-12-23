package com.ddd.pollpoll.designsystem.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PollProgressBar() {
    LinearProgressIndicator(progress = a)
//    val infiniteTransition = rememberInfiniteTransition()
//    // Fractional position of the 'head' and 'tail' of the two lines drawn, i.e. if the head is 0.8
//    // and the tail is 0.2, there is a line drawn from beteen 20% along to 80% along the total
//    // width.
//    val firstLineHead = infiniteTransition.animateFloat(
//        0f,
//        1f,
//        infiniteRepeatable(
//            animation = keyframes {
//                durationMillis = LinearAnimationDuration
//                0f at FirstLineHeadDelay with FirstLineHeadEasing
//                1f at FirstLineHeadDuration + FirstLineHeadDelay
//            }
//        )
//    )
//    val firstLineTail = infiniteTransition.animateFloat(
//        0f,
//        1f,
//        infiniteRepeatable(
//            animation = keyframes {
//                durationMillis = LinearAnimationDuration
//                0f at FirstLineTailDelay with FirstLineTailEasing
//                1f at FirstLineTailDuration + FirstLineTailDelay
//            }
//        )
//    )
//    val secondLineHead = infiniteTransition.animateFloat(
//        0f,
//        1f,
//        infiniteRepeatable(
//            animation = keyframes {
//                durationMillis = LinearAnimationDuration
//                0f at SecondLineHeadDelay with SecondLineHeadEasing
//                1f at SecondLineHeadDuration + SecondLineHeadDelay
//            }
//        )
//    )
//    val secondLineTail = infiniteTransition.animateFloat(
//        0f,
//        1f,
//        infiniteRepeatable(
//            animation = keyframes {
//                durationMillis = LinearAnimationDuration
//                0f at SecondLineTailDelay with SecondLineTailEasing
//                1f at SecondLineTailDuration + SecondLineTailDelay
//            }
//        )
//    )
    Canvas(
        Modifier
            .progressSemantics()
            .size(LinearIndicatorWidth, LinearIndicatorHeight)
    ) {
        val strokeWidth = size.height
        drawLinearIndicatorTrack(trackColor, strokeWidth)
        if (firstLineHead.value - firstLineTail.value > 0) {
            drawLinearIndicator(
                firstLineHead.value,
                firstLineTail.value,
                color,
                strokeWidth
            )
        }
        if (secondLineHead.value - secondLineTail.value > 0) {
            drawLinearIndicator(
                secondLineHead.value,
                secondLineTail.value,
                color,
                strokeWidth
            )
        }
    }
}
