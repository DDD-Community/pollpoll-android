package com.ddd.pollpoll.ui.feature

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ddd.pollpoll.designsystem.component.PollCardType
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

    val scrollState = rememberScrollState()
    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F1F1))
            .verticalScroll(scrollState)
    ) {
        TopScreen(categoryUiState.value)
        Spacer(modifier = Modifier.height(20.dp))
        PopularListScreen(popularUiState.value)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.PopularListScreen(popularUiState: PopularUiState) {
    val horizontalState = rememberPagerState()
    Column() {
        Surface(shape = RoundedCornerShape(20.dp)) {
            Spacer(modifier = Modifier.height(30.dp))
            Column() {
                when (popularUiState) {
                    PopularUiState.Loading -> {}
                    is PopularUiState.Success -> {
                        HorizontalPager(state = horizontalState, pageCount = 3) { page ->
                            when (page) {
                                0 -> PopularScreen(topTitle = "참여가 많은 폴폴")
                                1 -> PopularScreen(topTitle = "많이 구경 중인 폴폴")
                                2 -> PopularScreen(topTitle = "곧 종료되는 폴폴")
                            }
                        }
                        popularUiState.categoryList
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
        Image(painter = painterResource(id = PollIcon.MyPollPollTriangle), contentDescription = "")
    }
}

@Composable
fun PopularScreen(
    topTitle: String = "참여가 많은 폴폴",
    type: PollCardType = PollCardType.AB,
    title: String = "사무실에서 손톱 깎는거 잘못이다/아니다",
    participants: Int = 0,
    watcherCount: Int = 0
) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Text(
            text = topTitle,
            style = PollPollTheme.typography.heading02,
            color = PollPollTheme.colors.gray_900
        )
        Spacer(modifier = Modifier.height(12.dp))
        PollPopularCard()
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
                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF1F1F1)),
                            modifier = Modifier.size(45.dp),
                            shape = RoundedCornerShape(12.dp),
                            onClick = { /*TODO*/ }
                        ) {
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
