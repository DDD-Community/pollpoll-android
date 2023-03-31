package com.ddd.pollpoll.ui.feature

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.ddd.pollpoll.Post
import com.ddd.pollpoll.core.modifer.shadow
import com.ddd.pollpoll.designsystem.component.*
import com.ddd.pollpoll.designsystem.component.PollCardType
import com.ddd.pollpoll.designsystem.component.PollCategoryButton
import com.ddd.pollpoll.designsystem.component.PollPopularCard
import com.ddd.pollpoll.designsystem.component.PollRoundedButton
import com.ddd.pollpoll.designsystem.icon.PollIcon
import com.ddd.pollpoll.designsystem.theme.PollPollTheme
import com.ddd.pollpoll.feature.mypollpoll.ui.PollCard

@Composable
internal fun MainScreenRoute(
    viewModel: MainViewModel = hiltViewModel(),
    navigateToReadVote: (Int) -> Unit,
) {
    val categoryUiState = viewModel.categoryUiState.collectAsStateWithLifecycle().value
    val popularUiState = viewModel.popularUiState.collectAsStateWithLifecycle().value
    val posts = viewModel.posts.collectAsState().value

    MainScreen(categoryUiState, popularUiState, posts, navigateToReadVote)
}

@Composable
private fun MainScreen(
    categoryUiState: CategoryUiState,
    popularUiState: PopularUiState,
    posts: List<Post>,
    navigateToReadVote: (Int) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F1F1))
            .verticalScroll(scrollState),
    ) {
        TopScreen(categoryUiState)
        Spacer(modifier = Modifier.height(20.dp))
        PopularListScreen(popularUiState)
        PollList(posts = posts, navigateToReadVote)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.PopularListScreen(popularUiState: PopularUiState) {
    val horizontalState = rememberPagerState()
    Column() {
        Surface(shape = RoundedCornerShape(20.dp)) {
            Column() {
                when (popularUiState) {
                    PopularUiState.Loading -> {
                    }

                    is PopularUiState.Success -> {
                        val horizontalState = rememberPagerState()
                        Spacer(modifier = Modifier.height(30.dp))
                        PollPagerIndicator(
                            numberOfPages = 3,
                            selectedPage = horizontalState.currentPage,
                            modifier = Modifier.padding(horizontal = 20.dp),
                        )
                        Spacer(modifier = Modifier.height(30.dp))

                        HorizontalPager(state = horizontalState, pageCount = 3) { page ->
                            when (page) {
                                0 -> PopularScreen(topTitle = "참여가 많은 폴폴")
                                1 -> PopularScreen(topTitle = "많이 구경 중인 폴폴")
                                2 -> PopularScreen(topTitle = "곧 종료되는 폴폴")
                            }
                        }
                        popularUiState.categoryList
                    }

                    is PopularUiState.Error -> {}
                }
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
//        Image(painter = painterResource(id = PollIcon.MyPollPollTriangle), contentDescription = "")
        Image(
            imageVector = ImageVector.vectorResource(id = PollIcon.MyPollPollTriangle),
            contentDescription = null,
            modifier = Modifier
                .offset(y = (-10).dp)
                .align(Alignment.CenterHorizontally),
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
        PollPopularCard()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopScreen(categoryUiState: CategoryUiState = CategoryUiState.Success(listOf())) {
    val listState = rememberScrollState()
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
                    onClick = { /*TODO*/ },
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
                    Row(modifier = Modifier.horizontalScroll(listState)) {
                        categoryUiState.categoryList.forEach {
                            PollCategoryButton(
                                imageUrl = it.imageUrl,
                                text = it.name,
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

@Composable
fun PollList(posts: List<Post>, navigateToReadVote: (Int) -> Unit) {
    for (post in posts) {
        PollCard(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            post = post,
            onClick = { navigateToReadVote(post.postId) },
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    PollPollTheme() {
        MainScreen(
            categoryUiState = CategoryUiState.Loading,
            popularUiState = PopularUiState.Loading,
            posts = listOf(),
            navigateToReadVote = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopScreenPreview() {
    PollPollTheme() {
        TopScreen(categoryUiState = CategoryUiState.Loading)
    }
}
