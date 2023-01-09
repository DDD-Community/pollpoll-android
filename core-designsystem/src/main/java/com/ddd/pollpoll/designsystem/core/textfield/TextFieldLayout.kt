package com.ddd.pollpoll.designsystem.core.textfield

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
@ExperimentalMaterial3Api
internal fun TextFieldLayout(
    modifier: Modifier,
    textField: @Composable () -> Unit,
    label: @Composable (() -> Unit)?,
    placeholder: @Composable ((Modifier) -> Unit)?,
    leading: @Composable (() -> Unit)?,
    trailing: @Composable (() -> Unit)?,
    singleLine: Boolean,
    animationProgress: Float,
    container: @Composable () -> Unit,
    supporting: @Composable (() -> Unit)?,
    paddingValues: PaddingValues
) {
    val measurePolicy = remember(singleLine, animationProgress, paddingValues) {
        TextFieldMeasurePolicy(singleLine, animationProgress, paddingValues)
    }
    val layoutDirection = LocalLayoutDirection.current
    Layout(
        modifier = modifier,
        content = {
            // The container is given as a Composable instead of a background modifier so that
            // elements like supporting text can be placed outside of it while still contributing
            // to the text field's measurements overall.
            container()

            if (leading != null) {
                Box(
                    modifier = Modifier
                        .layoutId(LeadingId)
                        .then(IconDefaultSizeModifier),
                    contentAlignment = Alignment.Center
                ) {
                    leading()
                }
            }
            if (trailing != null) {
                Box(
                    modifier = Modifier
                        .layoutId(TrailingId)
                        .then(IconDefaultSizeModifier),
                    contentAlignment = Alignment.Center
                ) {
                    trailing()
                }
            }

            val startTextFieldPadding = paddingValues.calculateStartPadding(layoutDirection)
            val endTextFieldPadding = paddingValues.calculateEndPadding(layoutDirection)
            val padding = Modifier.padding(
                start = if (leading != null) {
                    (startTextFieldPadding - HorizontalIconPadding).coerceAtLeast(
                        0.dp
                    )
                } else {
                    startTextFieldPadding
                },
                end = if (trailing != null) {
                    (endTextFieldPadding - HorizontalIconPadding).coerceAtLeast(0.dp)
                } else {
                    endTextFieldPadding
                }
            )
            if (placeholder != null) {
                placeholder(
                    Modifier
                        .layoutId(PlaceholderId)
                        .then(padding)
                )
            }
            if (label != null) {
                Box(
                    Modifier
                        .layoutId(LabelId)
                        .then(padding)
                ) { label() }
            }
            Box(
                modifier = Modifier
                    .layoutId(TextFieldId)
                    .then(padding),
                propagateMinConstraints = true
            ) {
                textField()
            }

            if (supporting != null) {
                Box(
                    Modifier
                        .layoutId(SupportingId)
                        .padding(supportingTextPadding())
                ) { supporting() }
            }
        },
        measurePolicy = measurePolicy
    )
}

class TextFieldMeasurePolicy(
    private val singleLine: Boolean,
    private val animationProgress: Float,
    private val paddingValues: PaddingValues
) : MeasurePolicy {
    override fun MeasureScope.measure(
        measurables: List<Measurable>,
        constraints: Constraints
    ): MeasureResult {
        val topPaddingValue = paddingValues.calculateTopPadding().roundToPx()
        val bottomPaddingValue = paddingValues.calculateBottomPadding().roundToPx()

        var occupiedSpaceHorizontally = 0
        var occupiedSpaceVertically = 0

        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        // measure leading icon
        val leadingPlaceable =
            measurables.find { it.layoutId == LeadingId }?.measure(looseConstraints)
        occupiedSpaceHorizontally += widthOrZero(
            leadingPlaceable
        )
        occupiedSpaceVertically =
            Integer.max(occupiedSpaceVertically, heightOrZero(leadingPlaceable))

        // measure trailing icon
        val trailingPlaceable = measurables.find { it.layoutId == TrailingId }
            ?.measure(looseConstraints.offset(horizontal = -occupiedSpaceHorizontally))
        occupiedSpaceHorizontally += widthOrZero(
            trailingPlaceable
        )
        occupiedSpaceVertically =
            Integer.max(occupiedSpaceVertically, heightOrZero(trailingPlaceable))

        // measure label
        val labelConstraints = looseConstraints
            .offset(
                vertical = -bottomPaddingValue,
                horizontal = -occupiedSpaceHorizontally
            )
        val labelPlaceable =
            measurables.find { it.layoutId == LabelId }?.measure(labelConstraints)

        // measure input field
        val effectiveTopOffset = topPaddingValue + heightOrZero(labelPlaceable)
        val verticalConstraintOffset = -effectiveTopOffset - bottomPaddingValue
        val textFieldConstraints = constraints
            .copy(minHeight = 0)
            .offset(
                vertical = verticalConstraintOffset,
                horizontal = -occupiedSpaceHorizontally
            )
        val textFieldPlaceable = measurables
            .first { it.layoutId == TextFieldId }
            .measure(textFieldConstraints)

        // measure placeholder
        val placeholderConstraints = textFieldConstraints.copy(minWidth = 0)
        val placeholderPlaceable = measurables
            .find { it.layoutId == PlaceholderId }
            ?.measure(placeholderConstraints)

        occupiedSpaceVertically = Integer.max(
            occupiedSpaceVertically,
            Integer.max(heightOrZero(textFieldPlaceable), heightOrZero(placeholderPlaceable)) +
                effectiveTopOffset + bottomPaddingValue
        )

        // measure supporting text
        val supportingConstraints = looseConstraints.offset(
            vertical = -occupiedSpaceVertically
        ).copy(minHeight = 0)
        val supportingPlaceable =
            measurables.find { it.layoutId == SupportingId }?.measure(supportingConstraints)
        val supportingHeight = heightOrZero(supportingPlaceable)

        val width = calculateWidth(
            leadingWidth = widthOrZero(leadingPlaceable),
            trailingWidth = widthOrZero(trailingPlaceable),
            textFieldWidth = textFieldPlaceable.width,
            labelWidth = widthOrZero(labelPlaceable),
            placeholderWidth = widthOrZero(placeholderPlaceable),
            constraints = constraints
        )
        val totalHeight = calculateHeight(
            textFieldHeight = textFieldPlaceable.height,
            labelHeight = heightOrZero(labelPlaceable),
            leadingHeight = heightOrZero(leadingPlaceable),
            trailingHeight = heightOrZero(trailingPlaceable),
            placeholderHeight = heightOrZero(placeholderPlaceable),
            supportingHeight = heightOrZero(supportingPlaceable),
            isLabelFocused = animationProgress == 1f,
            constraints = constraints,
            density = density,
            paddingValues = paddingValues
        )
        val height = totalHeight - supportingHeight

        val containerPlaceable = measurables.first { it.layoutId == ContainerId }.measure(
            Constraints(
                minWidth = if (width != Constraints.Infinity) width else 0,
                maxWidth = width,
                minHeight = if (height != Constraints.Infinity) height else 0,
                maxHeight = height
            )
        )

        return layout(width, totalHeight) {
            if (labelPlaceable != null) {
                placeWithLabel(
                    width = width,
                    totalHeight = totalHeight,
                    textfieldPlaceable = textFieldPlaceable,
                    labelPlaceable = labelPlaceable,
                    placeholderPlaceable = placeholderPlaceable,
                    leadingPlaceable = leadingPlaceable,
                    trailingPlaceable = trailingPlaceable,
                    containerPlaceable = containerPlaceable,
                    supportingPlaceable = supportingPlaceable,
                    singleLine = singleLine,
                    labelEndPosition = topPaddingValue,
                    textPosition = topPaddingValue + labelPlaceable.height,
                    animationProgress = animationProgress,
                    density = density
                )
            } else {
                placeWithoutLabel(
                    width = width,
                    totalHeight = totalHeight,
                    textPlaceable = textFieldPlaceable,
                    placeholderPlaceable = placeholderPlaceable,
                    leadingPlaceable = leadingPlaceable,
                    trailingPlaceable = trailingPlaceable,
                    containerPlaceable = containerPlaceable,
                    supportingPlaceable = supportingPlaceable,
                    singleLine = singleLine,
                    density = density,
                    paddingValues = paddingValues
                )
            }
        }
    }

    override fun IntrinsicMeasureScope.maxIntrinsicHeight(
        measurables: List<IntrinsicMeasurable>,
        width: Int
    ): Int {
        return intrinsicHeight(measurables, width) { intrinsicMeasurable, w ->
            intrinsicMeasurable.maxIntrinsicHeight(w)
        }
    }

    override fun IntrinsicMeasureScope.minIntrinsicHeight(
        measurables: List<IntrinsicMeasurable>,
        width: Int
    ): Int {
        return intrinsicHeight(measurables, width) { intrinsicMeasurable, w ->
            intrinsicMeasurable.minIntrinsicHeight(w)
        }
    }

    override fun IntrinsicMeasureScope.maxIntrinsicWidth(
        measurables: List<IntrinsicMeasurable>,
        height: Int
    ): Int {
        return intrinsicWidth(measurables, height) { intrinsicMeasurable, h ->
            intrinsicMeasurable.maxIntrinsicWidth(h)
        }
    }

    override fun IntrinsicMeasureScope.minIntrinsicWidth(
        measurables: List<IntrinsicMeasurable>,
        height: Int
    ): Int {
        return intrinsicWidth(measurables, height) { intrinsicMeasurable, h ->
            intrinsicMeasurable.minIntrinsicWidth(h)
        }
    }

    private fun intrinsicWidth(
        measurables: List<IntrinsicMeasurable>,
        height: Int,
        intrinsicMeasurer: (IntrinsicMeasurable, Int) -> Int
    ): Int {
        val textFieldWidth =
            intrinsicMeasurer(measurables.first { it.layoutId == TextFieldId }, height)
        val labelWidth = measurables.find { it.layoutId == LabelId }?.let {
            intrinsicMeasurer(it, height)
        } ?: 0
        val trailingWidth = measurables.find { it.layoutId == TrailingId }?.let {
            intrinsicMeasurer(it, height)
        } ?: 0
        val leadingWidth = measurables.find { it.layoutId == LeadingId }?.let {
            intrinsicMeasurer(it, height)
        } ?: 0
        val placeholderWidth = measurables.find { it.layoutId == PlaceholderId }?.let {
            intrinsicMeasurer(it, height)
        } ?: 0
        return calculateWidth(
            leadingWidth = leadingWidth,
            trailingWidth = trailingWidth,
            textFieldWidth = textFieldWidth,
            labelWidth = labelWidth,
            placeholderWidth = placeholderWidth,
            constraints = ZeroConstraints
        )
    }

    private fun IntrinsicMeasureScope.intrinsicHeight(
        measurables: List<IntrinsicMeasurable>,
        width: Int,
        intrinsicMeasurer: (IntrinsicMeasurable, Int) -> Int
    ): Int {
        val textFieldHeight =
            intrinsicMeasurer(measurables.first { it.layoutId == TextFieldId }, width)
        val labelHeight = measurables.find { it.layoutId == LabelId }?.let {
            intrinsicMeasurer(it, width)
        } ?: 0
        val trailingHeight = measurables.find { it.layoutId == TrailingId }?.let {
            intrinsicMeasurer(it, width)
        } ?: 0
        val leadingHeight = measurables.find { it.layoutId == LeadingId }?.let {
            intrinsicMeasurer(it, width)
        } ?: 0
        val placeholderHeight = measurables.find { it.layoutId == PlaceholderId }?.let {
            intrinsicMeasurer(it, width)
        } ?: 0
        val supportingHeight = measurables.find { it.layoutId == SupportingId }?.let {
            intrinsicMeasurer(it, width)
        } ?: 0
        return calculateHeight(
            textFieldHeight = textFieldHeight,
            labelHeight = labelHeight,
            leadingHeight = leadingHeight,
            trailingHeight = trailingHeight,
            placeholderHeight = placeholderHeight,
            supportingHeight = supportingHeight,
            isLabelFocused = animationProgress == 1f,
            constraints = ZeroConstraints,
            density = density,
            paddingValues = paddingValues
        )
    }
}

private fun calculateWidth(
    leadingWidth: Int,
    trailingWidth: Int,
    textFieldWidth: Int,
    labelWidth: Int,
    placeholderWidth: Int,
    constraints: Constraints
): Int {
    val middleSection = maxOf(
        textFieldWidth,
        labelWidth,
        placeholderWidth
    )
    val wrappedWidth = leadingWidth + middleSection + trailingWidth
    return max(wrappedWidth, constraints.minWidth)
}

private fun calculateHeight(
    textFieldHeight: Int,
    labelHeight: Int,
    leadingHeight: Int,
    trailingHeight: Int,
    placeholderHeight: Int,
    supportingHeight: Int,
    isLabelFocused: Boolean,
    constraints: Constraints,
    density: Float,
    paddingValues: PaddingValues
): Int {
    // Even though the padding is defined by the developer, it only affects the text field when the
    // text field is focused. Otherwise, we use the default value.
    val verticalPadding = density * if (isLabelFocused) {
        (paddingValues.calculateTopPadding() + paddingValues.calculateBottomPadding()).value
    } else {
//        (TextFieldPadding * 2).value
        (paddingValues.calculateTopPadding() + paddingValues.calculateBottomPadding()).value
    }

    val middleSectionHeight = if (isLabelFocused) {
        verticalPadding + labelHeight + max(textFieldHeight, placeholderHeight)
    } else {
        verticalPadding + maxOf(labelHeight, textFieldHeight, placeholderHeight)
    }
    return max(
        constraints.minHeight,
        maxOf(
            leadingHeight,
            trailingHeight,
            middleSectionHeight.roundToInt()
        ) + supportingHeight
    )
}

/**
 * Places the provided text field, placeholder, and label in the TextField given the PaddingValues
 * when there is a label. When there is no label, [placeWithoutLabel] is used instead.
 */
private fun Placeable.PlacementScope.placeWithLabel(
    width: Int,
    totalHeight: Int,
    textfieldPlaceable: Placeable,
    labelPlaceable: Placeable?,
    placeholderPlaceable: Placeable?,
    leadingPlaceable: Placeable?,
    trailingPlaceable: Placeable?,
    containerPlaceable: Placeable,
    supportingPlaceable: Placeable?,
    singleLine: Boolean,
    labelEndPosition: Int,
    textPosition: Int,
    animationProgress: Float,
    density: Float
) {
    // place container
    containerPlaceable.place(IntOffset.Zero)

    // Most elements should be positioned w.r.t the text field's "visual" height, i.e., excluding
    // the supporting text on bottom
    val height = totalHeight - heightOrZero(supportingPlaceable)

    leadingPlaceable?.placeRelative(
        0,
        Alignment.CenterVertically.align(leadingPlaceable.height, height)
    )
    trailingPlaceable?.placeRelative(
        width - trailingPlaceable.width,
        Alignment.CenterVertically.align(trailingPlaceable.height, height)
    )
    labelPlaceable?.let {
        // if it's a single line, the label's start position is in the center of the
        // container. When it's a multiline text field, the label's start position is at the
        // top with padding
        val startPosition = if (singleLine) {
            Alignment.CenterVertically.align(it.height, height)
        } else {
            // Even though the padding is defined by the developer, it only affects the text field
            // when the text field is focused. Otherwise, we use the default value.
            (TextFieldPadding.value * density).roundToInt()
        }
        val distance = startPosition - labelEndPosition
        val positionY = startPosition - (distance * animationProgress).roundToInt()
        it.placeRelative(widthOrZero(leadingPlaceable), positionY)
    }
    textfieldPlaceable.placeRelative(
        widthOrZero(leadingPlaceable),
        textPosition
    )
    placeholderPlaceable?.placeRelative(
        widthOrZero(leadingPlaceable),
        textPosition
    )

    supportingPlaceable?.placeRelative(0, height)
}

/**
 * Places the provided text field and placeholder in [TextField] when there is no label. When
 * there is a label, [placeWithLabel] is used
 */
private fun Placeable.PlacementScope.placeWithoutLabel(
    width: Int,
    totalHeight: Int,
    textPlaceable: Placeable,
    placeholderPlaceable: Placeable?,
    leadingPlaceable: Placeable?,
    trailingPlaceable: Placeable?,
    containerPlaceable: Placeable,
    supportingPlaceable: Placeable?,
    singleLine: Boolean,
    density: Float,
    paddingValues: PaddingValues
) {
    // place container
    containerPlaceable.place(IntOffset.Zero)

    // Most elements should be positioned w.r.t the text field's "visual" height, i.e., excluding
    // the supporting text on bottom
    val height = totalHeight - heightOrZero(supportingPlaceable)
    val topPadding = (paddingValues.calculateTopPadding().value * density).roundToInt()

    leadingPlaceable?.placeRelative(
        0,
        Alignment.CenterVertically.align(leadingPlaceable.height, height)
    )
    trailingPlaceable?.placeRelative(
        width - trailingPlaceable.width,
        Alignment.CenterVertically.align(trailingPlaceable.height, height)
    )

    // Single line text field without label places its input center vertically. Multiline text
    // field without label places its input at the top with padding
    val textVerticalPosition = if (singleLine) {
        Alignment.CenterVertically.align(textPlaceable.height, height)
    } else {
        topPadding
    }
    textPlaceable.placeRelative(
        widthOrZero(leadingPlaceable),
        textVerticalPosition
    )

    // placeholder is placed similar to the text input above
    placeholderPlaceable?.let {
        val placeholderVerticalPosition = if (singleLine) {
            Alignment.CenterVertically.align(placeholderPlaceable.height, height)
        } else {
            topPadding
        }
        it.placeRelative(
            widthOrZero(leadingPlaceable),
            placeholderVerticalPosition
        )
    }

    supportingPlaceable?.placeRelative(0, height)
}

/**
 * A draw modifier that draws a bottom indicator line in [TextField]
 */
internal fun Modifier.drawIndicatorLine(indicatorBorder: BorderStroke): Modifier {
    val strokeWidthDp = indicatorBorder.width
    return drawWithContent {
        drawContent()
        if (strokeWidthDp == Dp.Hairline) return@drawWithContent
        val strokeWidth = strokeWidthDp.value * density
        val y = size.height - strokeWidth / 2
        drawLine(
            indicatorBorder.brush,
            Offset(0f, y),
            Offset(size.width, y),
            strokeWidth
        )
    }
}

internal fun supportingTextPadding(
    start: Dp = TextFieldPadding,
    top: Dp = SupportingTopPadding,
    end: Dp = TextFieldPadding,
    bottom: Dp = 0.dp
): PaddingValues = PaddingValues(start, top, end, bottom)
