package com.ddd.pollpoll.feature.main

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.pollpoll.core.ui.PollCardLazyList
import com.ddd.pollpoll.designsystem.component.PollSearchBar
import com.ddd.pollpoll.designsystem.icon.PollIcon
import com.ddd.pollpoll.designsystem.icon.PollIcon.SearchEmpty
import com.ddd.pollpoll.designsystem.theme.PollPollTheme
import com.ddd.pollpoll.feature.main.model.PostUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun SearchScreenRoute(
    viewModel: SearchViewModel = hiltViewModel(),
    navigateToReadVote: (Int) -> Unit,
    backToMain: () -> Unit,
) {
    val posts = viewModel.posts.toImmutableList()
    val lazyColumnListState = rememberLazyListState()
    val listState = viewModel.listState
    val shouldStartPaginate = remember {
        derivedStateOf {
            viewModel.canPaginate && (
                // 현재 보이는것보다
                lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                    ?: -9
                ) >= (lazyColumnListState.layoutInfo.totalItemsCount - 1)
        }
    }

    SearchScreen(
        shouldStartPaginate = shouldStartPaginate.value,
        listState = listState,
        posts = posts,
        onBackButtonClicked = { backToMain() },
        onSearchButtonClicked = { viewModel.searchPost(it) },
        lazyColumnListState = lazyColumnListState,
        onPageStarted = { viewModel.getPost(it) },
        navigateToReadVote = navigateToReadVote,
    )
}

@Composable
fun SearchScreen(
    listState: ListState,
    posts: ImmutableList<PostUi> = persistentListOf(),
    lazyColumnListState: LazyListState,
    navigateToReadVote: (Int) -> Unit,
    onBackButtonClicked: () -> Unit = {},
    onSearchButtonClicked: (String) -> Unit = {},
    shouldStartPaginate: Boolean,
    onPageStarted: (String) -> Unit = {},
) {
    val context = LocalContext.current

    var textValue by remember { mutableStateOf("") }

    LaunchedEffect(key1 = shouldStartPaginate) {
        if (shouldStartPaginate && listState == ListState.IDLE) {
            onPageStarted(textValue)
        }
    }

    LaunchedEffect(key1 = listState) {
        when (listState) {
            ListState.IDLE -> {}
            ListState.LOADING -> {}
            ListState.ERROR -> Toast.makeText(context, "에러가 발생했습니다.", Toast.LENGTH_SHORT).show()
            ListState.NONE -> {}
            ListState.EMPTY -> {}
            ListState.PAGINATING -> {}
            ListState.PAGINATION_EXHAUST -> {}
        }
        if (shouldStartPaginate && listState == ListState.IDLE) {
            onPageStarted(textValue)
        }
    }

    Column() {
        SearchBarScreen(
            onBackButtonClicked = onBackButtonClicked,
            onSearchButtonClicked = { onSearchButtonClicked(textValue) },
            value = textValue,
            onValueChange = { textValue = it },
            onCloseButtonClicked = { textValue = "" },

        )
        LazyColumn(state = lazyColumnListState) {
            PollCardLazyList(posts, navigateToReadVote)
            when (listState) {
                ListState.IDLE -> {}

                ListState.LOADING -> item { LoadingScreen() }

                ListState.ERROR -> {}

                ListState.EMPTY -> item { emptySearchScreen() }

                ListState.PAGINATING -> {}

                ListState.PAGINATION_EXHAUST -> {}

                else -> {}
            }
        }
    }
}

@Composable
private fun SearchBarScreen(
    onBackButtonClicked: () -> Unit = {},
    onSearchButtonClicked: () -> Unit = {},
    value: String,
    onValueChange: (String) -> Unit,
    onCloseButtonClicked: () -> Unit,
) {
    PollPollTheme() {
        Row(Modifier.padding(horizontal = 20.dp, vertical = 30.dp)) {
            IconButton(onClick = { onBackButtonClicked() }) {
                Image(
                    painter = painterResource(id = PollIcon.LeftArrow),
                    contentDescription = "",
                )
            }
            PollSearchBar(
                value = value,
                onValueChange = onValueChange,
                onCloseButtonClicked = onCloseButtonClicked,
                onSearchButtonClicked = onSearchButtonClicked,
            )
        }
    }
}

@Composable
fun LazyItemScope.emptySearchScreen() = Box(
    Modifier.fillParentMaxSize(),
) {
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

@Composable
fun LazyItemScope.LoadingScreen() = Box(
    Modifier.fillParentMaxSize(),
) {
    Column(Modifier.align(Alignment.Center)) {
        CircularProgressIndicator(color = PollPollTheme.colors.primary_500)
    }
}

@Preview
@Composable
fun SearchBarScreenPreview() {
    SearchBarScreen(
        onBackButtonClicked = {},
        onSearchButtonClicked = {},
        value = "",
        onValueChange = {},
        onCloseButtonClicked = {},
    )
}

@Preview
@Composable
fun SearchScreenPreview() {
    PollPollTheme {
        SearchScreen(
            listState = ListState.IDLE,
            posts = fakePost,
            lazyColumnListState = rememberLazyListState(),
            navigateToReadVote = {},
            shouldStartPaginate = false,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenEmptyPreview() {
    PollPollTheme {
        SearchScreen(
            listState = ListState.IDLE,
            posts = persistentListOf(),
            lazyColumnListState = rememberLazyListState(),
            navigateToReadVote = {},
            shouldStartPaginate = false,
        )
    }
}

private val fakePost = persistentListOf<PostUi>(
    PostUi(
        categoryName = "Trent McFadden",
        contents = "regione",
        nickname = "Haley Sloan",
        participantCount = 6706,
        pollEndAt = 2140,
        pollId = 2143,
        pollItemCount = 3265,
        postCreatedAt = 4168,
        postHits = 3609,
        postId = 3571,
        pollItems = persistentListOf(),
        title = "mutat",
        watcherCount = 2567,
    ),
    PostUi(
        categoryName = "Trent McFadden",
        contents = "regione",
        nickname = "Haley Sloan",
        participantCount = 6706,
        pollEndAt = 2140,
        pollId = 2143,
        pollItemCount = 3265,
        postCreatedAt = 4168,
        postHits = 3609,
        postId = 3572,
        pollItems = persistentListOf(),
        title = "mutat",
        watcherCount = 2567,
    ),

)
