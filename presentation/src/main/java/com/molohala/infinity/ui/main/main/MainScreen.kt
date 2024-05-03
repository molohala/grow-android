package com.molohala.infinity.ui.main.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.molohala.infinity.ui.main.profile.ProfileScreen
import com.molohala.infinity.R
import com.molohala.infinity.designsystem.color.InfinityColor
import com.molohala.infinity.designsystem.extension.bounceClick
import com.molohala.infinity.extension.shadow
import com.molohala.infinity.ui.main.baekjoonrank.BaekjoonRankScreen
import com.molohala.infinity.ui.main.community.CommunityScreen
import com.molohala.infinity.ui.main.githubrank.GithubRankScreen
import com.molohala.infinity.ui.main.home.HomeScreen
import com.molohala.infinity.ui.root.AppViewModel

@Composable
fun MainScreen(
    navController: NavController,
    appViewModel: AppViewModel
) {
    val uiAppState by appViewModel.uiState.collectAsState()

    val mainViews = arrayListOf(
        BottomNavigationType.Home,
        BottomNavigationType.Community,
        BottomNavigationType.GithubRank,
        BottomNavigationType.Baekjoon,
        BottomNavigationType.Profile,
    )

    LaunchedEffect(Unit) {
        appViewModel.fetchProfile()
    }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        color = Color.Black,
                        alpha = 0.04f,
                        offsetY = 0.dp,
                        blur = 10.dp
                    )
                    .background(Color.White)
                    .padding(vertical = 12.dp, horizontal = 12.dp)
                    .padding(bottom = 16.dp)
            ) {
                mainViews.forEach { tab ->
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .bounceClick(onClick = {
                                appViewModel.clickTab(tab)
                            }),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(28.dp),
                            painter = painterResource(id = tab.icon),
                            contentDescription = tab.name,
                            tint = if (uiAppState.selectedTab == tab) InfinityColor.blue else Color.LightGray
                        )
                        Text(
                            modifier = Modifier,
                            text = tab.name,
                            style = MaterialTheme.typography.bodySmall,
                            color = if (uiAppState.selectedTab == tab) InfinityColor.blue else Color.Gray
                        )
                    }
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
        ) {
            when (uiAppState.selectedTab) {
                is BottomNavigationType.Home -> HomeScreen(navController = navController, appViewModel = appViewModel)
                is BottomNavigationType.Community -> CommunityScreen(navController = navController, appViewModel = appViewModel)
                BottomNavigationType.GithubRank -> GithubRankScreen(navController = navController, appViewModel = appViewModel)
                BottomNavigationType.Baekjoon -> BaekjoonRankScreen(navController = navController)
                BottomNavigationType.Profile -> ProfileScreen(navController = navController, appViewModel = appViewModel)
            }
        }
    }
}

sealed class BottomNavigationType(
    val name: String,
    val icon: Int
) {
    data object Home : BottomNavigationType(name = "홈", icon = R.drawable.ic_home)
    data object Community : BottomNavigationType(name = "커뮤니티", icon = R.drawable.ic_community)
    data object GithubRank : BottomNavigationType(name = "Github", icon = R.drawable.ic_github)
    data object Baekjoon : BottomNavigationType(name = "백준", icon = R.drawable.ic_baekjoon)
    data object Profile : BottomNavigationType(name = "프로필", icon = R.drawable.ic_profile)
}