package com.ddd.pollpoll.feature.vote

import android.os.Parcelable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.parcelize.Parcelize

internal class InsertVoteState @OptIn(
    ExperimentalMaterial3Api::class,
) constructor(
    val bottomSheetState: SheetState,
    val coroutineScope: CoroutineScope,
    var insertVoteStep: InsertVoteStep,
) {
    var confirmDialogState by mutableStateOf(false)
        private set

    var bottomState by mutableStateOf(false)
        private set

    var cancelDialogState by mutableStateOf(false)
        private set

    var progressState by mutableStateOf(0.0f)
        private set

    fun setProgressBar(progress: Float) {
        progressState = progress
    }

    fun openBottomSheet(open: Boolean) {
        bottomState = open
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
    bottomSheetState: SheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Hidden,
        skipHiddenState = false,
    ),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    insertVoteStep: InsertVoteStep = rememberInsertVoteStep(),
): InsertVoteState = remember(bottomSheetState, coroutineScope) {
    InsertVoteState(bottomSheetState, coroutineScope, insertVoteStep)
}

@Composable
fun rememberInsertVoteStep(): InsertVoteStep {
      =  rememberSaveable { mutableStateOf(InsertVoteStep.SelectCategory) }

}

sealed interface InsertVoteStep : Parcelable {
    @Parcelize
    object SelectCategory : InsertVoteStep

    @Parcelize
    object InsertTitle : InsertVoteStep

    @Parcelize
    object AddVoteCategory : InsertVoteStep
}
