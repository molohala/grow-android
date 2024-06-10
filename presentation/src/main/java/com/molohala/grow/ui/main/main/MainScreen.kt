package com.molohala.grow.ui.main.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.bestswlkh0310.mydesignsystem.component.bottomtabbar.BottomTabItem
import com.bestswlkh0310.mydesignsystem.component.bottomtabbar.MyBottomTabBar
import com.molohala.grow.R
import com.molohala.grow.ui.main.baekjoonrank.BaekjoonRankScreen
import com.molohala.grow.ui.main.forum.ForumScreen
import com.molohala.grow.ui.main.githubrank.GithubRankScreen
import com.molohala.grow.ui.main.home.HomeScreen
import com.molohala.grow.ui.main.profile.ProfileScreen
import com.molohala.grow.ui.root.AppViewModel
import com.bestswlkh0310.mydesignsystem.R as DR

data object Home: BottomTabItem(resId = DR.drawable.ic_home, text = "홈")
data object Forum: BottomTabItem(resId = DR.drawable.ic_chat, text = "포럼")
data object Github: BottomTabItem(resId = DR.drawable.ic_github, text = "Github")
data object Baekjoon: BottomTabItem(resId = DR.drawable.ic_baekjoon, text = "백준")
data object Profile: BottomTabItem(resId = DR.drawable.ic_person, text = "프로필")

@Composable
fun MainScreen(
    navController: NavController,
    appViewModel: AppViewModel
) {
    val uiAppState by appViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        appViewModel.fetchProfile()
    }

    Box {
        when (uiAppState.selectedTab) {
            Home -> HomeScreen(
                navController = navController,
                appViewModel = appViewModel
            )

            Forum -> ForumScreen(
                navController = navController,
                appViewModel = appViewModel
            )

            Github -> GithubRankScreen(
                navController = navController,
                appViewModel = appViewModel
            )

            Baekjoon -> BaekjoonRankScreen(
                navController = navController,
                appViewModel = appViewModel
            )

            Profile -> ProfileScreen(
                navController = navController,
                appViewModel = appViewModel
            )
        }
        Column {
            Spacer(modifier = Modifier.weight(1f))
            MyBottomTabBar(
                modifier = Modifier,
                selected = uiAppState.selectedTab,
                bottomTabList = listOf(Home, Forum, Github, Baekjoon, Profile)
            ) {
                appViewModel.clickTab(it)
            }
        }
    }
}