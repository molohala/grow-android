package com.molohala.infinity.ui.main.community

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.molohala.infinity.designsystem.color.InfinityColor
import com.molohala.infinity.common.constant.Constant
import com.molohala.infinity.common.flow.FetchFlow
import com.molohala.infinity.designsystem.commnuity.InfinityCommunityCell
import com.molohala.infinity.designsystem.commnuity.InfinityCommunityCellShimmer
import com.molohala.infinity.extension.bounceClick
import com.molohala.infinity.icon.IconAdd
import com.molohala.infinity.designsystem.typo.TopBar
import com.molohala.infinity.ui.root.AppViewModel

@Composable
fun CommunityScreen(
    navController: NavController,
    appViewModel: AppViewModel,
    communityViewModel: CommunityViewModel = viewModel()
) {
    val uiState by communityViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        communityViewModel.fetchCommunities()
    }

    TopBar(
        modifier = Modifier,
        backgroundColor = InfinityColor.background,
        text = "포럼"
    ) {
        Box(
            modifier = Modifier
                .background(InfinityColor.background)
        ) {
            when (uiState.communityFetchFlow) {
                FetchFlow.Failure -> Text(text = "불러오기 실패")
                FetchFlow.Fetching -> {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        repeat(4) {
                            InfinityCommunityCellShimmer()
                        }
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                FetchFlow.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        itemsIndexed(uiState.communities) { idx, community ->
                            InfinityCommunityCell(community = community, onAppear = {
                                uiState.communities.firstOrNull { it.community.communityId == community.community.communityId }?: return@InfinityCommunityCell
                                val interval = Constant.pageInterval
                                if (idx % interval == (interval - 1) && idx / interval == (uiState.communities.size - 1) / interval) {
                                    communityViewModel.fetchNextCommunities()
                                }
                            }) {

                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(32.dp))
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(end = 24.dp)
                            .padding(bottom = 24.dp)
                            .bounceClick(onClick = {

                            })
                            .align(Alignment.BottomEnd)
                            .height(48.dp)
                            .clip(CircleShape)
                            .background(InfinityColor.blue)
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        IconAdd()
                        Text(
                            modifier = Modifier
                                .padding(end = 4.dp),
                            text = "글쓰기",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}
