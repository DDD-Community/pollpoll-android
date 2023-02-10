package com.ddd.pollpoll.ui.feature

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ddd.pollpoll.designsystem.component.PollCategoryButton
import com.ddd.pollpoll.designsystem.component.PollPopularCard
import com.ddd.pollpoll.designsystem.icon.PollIcon
import com.ddd.pollpoll.designsystem.theme.PollPollTheme

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
internal fun MainScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val categoryUiState = viewModel.categoryUiState.collectAsStateWithLifecycle()
    val popularUiState = viewModel.popularUiState.collectAsStateWithLifecycle()

    Column(
        Modifier
            .fillMaxSize()
            .background(PollPollTheme.colors.gray_400)
    ) {
        TopScreen(categoryUiState.value)
        PopularScreen(popularUiState.value)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PopularScreen(popularUiState: PopularUiState) {
    val horizontalState = rememberPagerState()
    Surface(shape = RoundedCornerShape(20.dp) ) {
        when (popularUiState) {
            PopularUiState.Loading -> {}
            is PopularUiState.Success -> {
                HorizontalPager(state = horizontalState, pageCount = 3) { page ->
                    when (page) {
                        1 -> PollPopularCard()
                        2 -> PollPopularCard()
                        3 -> PollPopularCard()
                    }
                }
                popularUiState.categoryList
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopScreen(categoryUiState: CategoryUiState) {
    val listState = rememberScrollState()
    when (categoryUiState) {
        CategoryUiState.Loading -> {}
        is CategoryUiState.Success -> {
            Surface(shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)) {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Spacer(modifier = Modifier.height(62.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = PollIcon.Logo),
                            contentDescription = ""
                        )
                        Spacer(
                            modifier = Modifier
                                .width(10.dp)
                                .weight(1f)
                        )
                        Button(onClick = { /*TODO*/ }) {
                            Image(
                                painter = painterResource(id = PollIcon.Search),
                                contentDescription = ""
                            )
                        }
                    }
                    // 충격적이게도 LazyRow로 하면 망가짐
                    Row(modifier = Modifier.horizontalScroll(listState)) {
                        categoryUiState.categoryList.forEach {
                            PollCategoryButton(
                                imageUrl = it.imageUrl,
                                text = it.name
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
}

@Preview(showBackground = true)
@Composable
fun TopScreenPreview() {
    PollPollTheme() {
//        TopScreen(categoryUiState)
    }
}
