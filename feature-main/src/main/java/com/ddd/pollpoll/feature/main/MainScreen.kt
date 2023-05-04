package com.ddd.pollpoll.feature.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ddd.pollpoll.PollEndDate
import com.ddd.pollpoll.PopularPost
import com.ddd.pollpoll.Post
import com.ddd.pollpoll.core.modifer.shadow
import com.ddd.pollpoll.core.ui.PollCardLazyList
import com.ddd.pollpoll.designsystem.component.*
import com.ddd.pollpoll.designsystem.icon.PollIcon
import com.ddd.pollpoll.designsystem.theme.PollPollTheme
import com.ddd.pollpoll.feature.main.model.PostUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun MainScreenRoute(
    viewModel: MainViewModel = hiltViewModel(),
    navigateToReadVote: (Int) -> Unit,
    navigateToSearch: () -> Unit,
    scrollItem: (Boolean) -> Unit,
) {
    val mainUiState = viewModel.mainUiState.collectAsStateWithLifecycle().value
    val posts = viewModel.posts.toImmutableList()
    val lazyColumnListState = rememberLazyListState()

    // 왜 계속 호출할까?
    val isScrolled by remember { derivedStateOf { lazyColumnListState.firstVisibleItemIndex > 2 } }

    LaunchedEffect(key1 = isScrolled) {
        scrollItem(isScrolled)
    }

    val shouldStartPaginate = remember {
        derivedStateOf {
            viewModel.canPaginate && (
                // 현재 보이는것보다
                lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                    ?: -9
                ) >= (lazyColumnListState.layoutInfo.totalItemsCount - 1)
        }
    }

    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (shouldStartPaginate.value && viewModel.listState == ListState.IDLE) {
            viewModel.getPost()
        }
    }

    MainScreen(
        mainUiState.categoryUiState,
        mainUiState.popularUiState,
        mainUiState.selectedCategory,
        posts,
        navigateToReadVote,
        lazyColumnListState,
        navigateToSearch,
        viewModel::selectCategory,
    )
}

@Composable
private fun MainScreen(
    categoryUiState: CategoryUiState,
    popularUiState: PopularUiState,
    selectedCategory: Int? = null,
    posts: ImmutableList<PostUi> = persistentListOf(),
    navigateToReadVote: (Int) -> Unit,
    lazyColumnListState: LazyListState,
    onSearchClick: () -> Unit = {},
    onCategoryClicked: (Int?) -> Unit = {},
) {
    val categoryHorizontalState = rememberScrollState()
    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F1F1)),
        state = lazyColumnListState,
    ) {
        item {
            TopScreen(
                selectedCategory,
                categoryUiState,
                onSearchClick = onSearchClick,
                categoryHorizontalState,
                onCategoryClicked = onCategoryClicked,
            )
            Spacer(modifier = Modifier.height(20.dp))
            PopularListScreen(popularUiState)
        }
        PollCardLazyList(posts, navigateToReadVote)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PopularListScreen(popularUiState: PopularUiState) {
    val horizontalState = rememberPagerState()
    Column() {
        Surface(shape = RoundedCornerShape(20.dp)) {
            Column(Modifier.background(Color.White)) {
                Spacer(modifier = Modifier.height(30.dp))
                PollPagerIndicator(
                    numberOfPages = 3,
                    selectedPage = horizontalState.currentPage,
                    modifier = Modifier.padding(horizontal = 20.dp),
                )
                Spacer(modifier = Modifier.height(30.dp))

                HorizontalPager(state = horizontalState, pageCount = 3) { page ->
                    when (popularUiState) {
                        is PopularUiState.Success -> {
                            when (page) {
                                0 -> {
                                    val result = popularUiState.popularPostList.mostParticipatePost
                                    PopularScreen(
                                        topTitle = "참여가 많은 폴폴",
                                        title = result.title,
                                        type = if (result.isAB) PollCardType.AB else PollCardType.CHOICE,
                                        participants = result.participantCount,
                                        watcherCount = result.watcherCount,
                                        endDate = result.pollEndAt,
                                    )
                                }

                                1 -> {
                                    val result = popularUiState.popularPostList.mostWatchPost
                                    PopularScreen(
                                        topTitle = "많이 구경 중인 폴폴",
                                        type = if (result.isAB) PollCardType.AB else PollCardType.CHOICE,
                                        title = result.title,
                                        participants = result.participantCount,
                                        watcherCount = result.watcherCount,
                                        endDate = result.pollEndAt,
                                    )
                                }

                                2 -> {
                                    val result = popularUiState.popularPostList.endingSoonPost
                                    PopularScreen(
                                        topTitle = "곧 종료되는 폴폴",
                                        type = if (result.isAB) PollCardType.AB else PollCardType.CHOICE,
                                        title = result.title,
                                        participants = result.participantCount,
                                        watcherCount = result.watcherCount,
                                        endDate = result.pollEndAt,
                                    )
                                }
                            }
                        }

                        is PopularUiState.Loading -> {
                            PopularScreen(
                                isLoading = true,
                            )
                        }

                        is PopularUiState.Error -> {}
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
//        Image(painter = painterResource(id = PollIcon.MyPollPollTriangle), contentDescription = "")
        Image(
            imageVector = ImageVector.vectorResource(id = PollIcon.MyPollPollTriangle),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset(y = (-10).dp),
        )
    }
}

@Composable
fun PopularScreen(
    topTitle: String = "참여가 많은 폴폴",
    type: PollCardType = PollCardType.AB,
    title: String = "사무실에서 손톱 깎는거 잘못이다/아니다",
    participants: Int = 0,
    watcherCount: Int = 0,
    isLoading: Boolean = false,
    endDate: PollEndDate = PollEndDate(0),
) {
    Spacer(modifier = Modifier.height(30.dp))
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .background(Color.White),
    ) {
        Text(
            text = topTitle,
            style = PollPollTheme.typography.heading02,
            color = PollPollTheme.colors.gray_900,
        )
        Spacer(modifier = Modifier.height(12.dp))
        PollPopularCard(
            modifier = Modifier,
            type = type,
            title = title,
            participants = participants,
            watcherCount = watcherCount,
            state = if (isLoading) PollCardState.Loading else PollCardState.Success,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopScreen(
    selectedCategory: Int?,
    categoryUiState: CategoryUiState = CategoryUiState.Success(listOf()),
    onSearchClick: () -> Unit = {},
    categoryHorizontalState: ScrollState,
    onCategoryClicked: (Int?) -> Unit = {},
) {
    Surface(shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 20.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = PollIcon.Logo),
                    contentDescription = "",
                )
                Spacer(
                    modifier = Modifier
                        .width(10.dp)
                        .weight(1f),
                )
                PollRoundedButton(
                    modifier = Modifier
                        .size(45.dp)
                        .shadow(
                            color = Color(0x4DB3B8CA),
                            borderRadius = 12.dp,
                            blurRadius = 10.dp,
                            offsetY = 4.dp,
                            spread = 0.dp,
                        )
                        .clip(
                            RoundedCornerShape(12.dp),
                        ),
                    onClick = { onSearchClick() },
                    contentPadding = PaddingValues(0.dp),
                ) {
                    Image(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        painter = painterResource(id = PollIcon.Search),
                        contentDescription = "",
                    )
                }
            }
            Text(
                text = "카테고리",
                style = PollPollTheme.typography.heading04,
            )

            Spacer(modifier = Modifier.size(10.dp))
            when (categoryUiState) {
                CategoryUiState.Loading -> {}
                is CategoryUiState.Success -> {
                    // 충격적이게도 LazyRow로 하면 망가짐
                    Row(modifier = Modifier.horizontalScroll(categoryHorizontalState)) {
                        categoryUiState.categoryList.forEach {
                            PollCategoryButton(
                                imageUrl = it.imageUrl,
                                text = it.name,
                                clicked = it.categoryId == selectedCategory,
                                onClick = { onCategoryClicked(it.categoryId) },
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                }

                is CategoryUiState.Error -> {}
            }
        }
    }
}

@Preview
@Composable
fun PopularListScreenPreview() {
    PollPollTheme {
        PopularListScreen(
            PopularUiState.Success(
                popularPostList = PopularPost(
                    mostParticipatePost = Post(
                        categoryName = "Noreen Hood",
                        contents = "facilisis",
                        nickname = "Dale Kelly",
                        participantCount = 9958,
                        pollEndAt = PollEndDate(8622),
                        pollId = 4760,
                        pollItemCount = 2204,
                        postCreatedAt = 1871,
                        postHits = 3801,
                        postId = 6697,
                        pollItems = listOf(),
                        title = "gubergren",
                        watcherCount = 8388,
                    ),
                    mostWatchPost = Post(
                        categoryName = "Basil Horne",
                        contents = "pri",
                        nickname = "Sallie Watkins",
                        participantCount = 8429,
                        pollEndAt = PollEndDate(3946),
                        pollId = 3328,
                        pollItemCount = 5216,
                        postCreatedAt = 5874,
                        postHits = 9078,
                        postId = 5437,
                        pollItems = listOf(),
                        title = "mattis",
                        watcherCount = 7583,
                    ),
                    endingSoonPost = Post(
                        categoryName = "Basil Horne",
                        contents = "pri",
                        nickname = "Sallie Watkins",
                        participantCount = 8429,
                        pollEndAt = PollEndDate(3946),
                        pollId = 3328,
                        pollItemCount = 5216,
                        postCreatedAt = 5874,
                        postHits = 9078,
                        postId = 5437,
                        pollItems = listOf(),
                        title = "mattis",
                        watcherCount = 7583,
                    ),
                ),
            ),
        )
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    PollPollTheme() {
        val lazyColumnListState = rememberLazyListState()
        MainScreen(
            categoryUiState = CategoryUiState.Loading,
            popularUiState = PopularUiState.Loading,
            posts = persistentListOf(),
            navigateToReadVote = {},
            lazyColumnListState = lazyColumnListState,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopScreenPreview() {
    val scrollState = rememberScrollState()
    PollPollTheme() {
        TopScreen(
            selectedCategory = 1,
            categoryUiState = CategoryUiState.Loading,
            categoryHorizontalState = scrollState,
        )
    }
}
