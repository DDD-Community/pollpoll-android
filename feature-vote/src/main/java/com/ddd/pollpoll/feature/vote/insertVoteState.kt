package com.ddd.pollpoll.feature.vote

import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope

internal class InsertVoteState @OptIn(
    ExperimentalMaterial3Api::class,
) constructor(
    val bottomSheetState: BottomSheetScaffoldState,
    val coroutineScope: CoroutineScope,
) {
    var insertVoteStep by mutableStateOf<InsertVoteStep>(InsertVoteStep.SelectCategory)
        private set
    var confirmDialogState by mutableStateOf(false)
        private set

    var cancelDialogState by mutableStateOf(false)
        private set

    var progressState by mutableStateOf(0.0f)
        private set

    fun setProgressBar(progress: Float) {
        progressState = progress
    }

    fun setShowingConfirmDialog(shouldShow: Boolean) {
        confirmDialogState = shouldShow
    }

    fun setShowingCancelDialog(shouldShow: Boolean) {
        cancelDialogState = shouldShow
    }

    fun navigateAddVoteCategory() {
        insertVoteStep = InsertVoteStep.AddVoteCategory
    }

    fun navigateInsertTitle() {
        insertVoteStep = InsertVoteStep.InsertTitle
    }

    fun backInsertScreen() {
        insertVoteStep = when (insertVoteStep) {
            InsertVoteStep.AddVoteCategory -> InsertVoteStep.InsertTitle
            InsertVoteStep.InsertTitle -> InsertVoteStep.SelectCategory
            InsertVoteStep.SelectCategory -> {
                setShowingCancelDialog(true)
                InsertVoteStep.SelectCategory
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun rememberInsertVoteState(
    bottomSheetState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = SheetState(
            skipPartiallyExpanded = true,
            initialValue = SheetValue.Hidden,
        ),
    ),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): InsertVoteState = remember(bottomSheetState, coroutineScope) {
    InsertVoteState(bottomSheetState, coroutineScope)
}

sealed interface InsertVoteStep {
    object SelectCategory : InsertVoteStep
    object InsertTitle : InsertVoteStep
    object AddVoteCategory : InsertVoteStep
}
