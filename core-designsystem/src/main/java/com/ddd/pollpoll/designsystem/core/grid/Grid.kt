package com.ddd.pollpoll.designsystem.core.grid

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun VerticalGrid(
    modifier: Modifier = Modifier,
    columns: Int = 2,
    cellPadding: PaddingValues = PaddingValues(bottom = 15.dp),
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->

        // 부모가 전체이기에 절반을 가져온다.
        // child는 부모의 절반에서 80.dp를 뺀값이다.
        val itemWidth = ((constraints.maxWidth / columns) - 80.dp.toPx()).roundToInt()

        // 아래의 measuble을 절반
        val itemConstraints = constraints.copy(
            minWidth = itemWidth,
            maxWidth = itemWidth
        )
        // Measure each item with these constraints
        val placeables = measurables.map { it.measure(itemConstraints) }
        // Track each columns height so we can calculate the overall height
        val columnHeights = Array(columns) { 0 }
        placeables.forEachIndexed { index, placeable ->
            val column = index % columns
            columnHeights[column] += placeable.height
        }
        val height = (columnHeights.maxOrNull() ?: constraints.minHeight).coerceAtMost(constraints.maxHeight)
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight
        ) {
            // Track the Y co-ord per column we have placed up to
            val columnY = Array(columns) { 0 }
            placeables.forEachIndexed { index, placeable ->
                val column = index % columns

                // 이건 조금더 생각해보자
                placeable.placeRelative(
                    x = column * itemWidth + 80.dp.toPx().roundToInt(),

                    y = columnY[column]
                )

                columnY[column] += placeable.height + cellPadding.calculateBottomPadding().toPx()
                    .roundToInt()

                Log.d("test", columnY[column].toString())
            }
        }
    }
}
