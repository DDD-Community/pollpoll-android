package com.ddd.pollpoll.ui.feature

import androidx.lifecycle.ViewModel
import com.ddd.pollpoll.core.data.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val postRepository: PostRepository,
) : ViewModel() {
}
