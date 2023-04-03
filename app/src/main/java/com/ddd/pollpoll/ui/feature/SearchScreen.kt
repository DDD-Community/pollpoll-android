package com.ddd.pollpoll.ui.feature

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.pollpoll.Post
import com.ddd.pollpoll.designsystem.icon.PollIcon.SearchEmpty
import com.ddd.pollpoll.designsystem.theme.PollPollTheme

@Composable
fun SearchScreenRoute(
    viewModel: SearchViewModel = hiltViewModel(),
) {
}

@Composable
private fun SearchScreen(
    categoryUiState: CategoryUiState,
    popularUiState: PopularUiState,
    posts: List<Post>,
    navigateToReadVote: (Int) -> Unit,
    lazyColumnListState: LazyListState,
) {
}


//private fun SearchBar

@Composable
fun emptySearchScreen() {
    Box() {
        Column(Modifier.align(Alignment.Center)) {
            Image(painter = painterResource(id = SearchEmpty), contentDescription = "")
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "검색결과가 없습니다.\n" + "다른 검색어를 입력해보세요.",
                textAlign = TextAlign.Center,
                style = PollPollTheme.typography.body03,
            )
        }
    }
}

@Preview
@Composable
fun emptySearchScreenPreview() {
    PollPollTheme {
        emptySearchScreen()
    }
}
