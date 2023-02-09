package com.ddd.pollpoll.feature.vote

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope

@Stable
internal class InsertVoteState @OptIn(ExperimentalMaterialApi::class) constructor(
    val bottomSheetState: ModalBottomSheetState,
    val coroutineScope: CoroutineScope
) {

    var dialogState by mutableStateOf(false)
        private set

    var progressState by mutableStateOf(0.0f)
        private set

    fun setProgressBar(progress: Float) {
        progressState = progress
    }

    fun setShowDialog(shouldShow: Boolean) {
        dialogState = shouldShow
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun rememberInsertVoteState(
    bottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): InsertVoteState = remember(bottomSheetState, coroutineScope) {
    InsertVoteState(bottomSheetState, coroutineScope)
}
