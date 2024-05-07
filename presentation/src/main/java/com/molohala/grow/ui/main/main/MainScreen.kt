package com.molohala.grow.ui.main.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.molohala.grow.designsystem.component.bottomtabbar.BottomTabItemType
import com.molohala.grow.designsystem.component.bottomtabbar.GrowBottomTabBar
import com.molohala.grow.ui.main.baekjoonrank.BaekjoonRankScreen
import com.molohala.grow.ui.main.forum.ForumScreen
import com.molohala.grow.ui.main.githubrank.GithubRankScreen
import com.molohala.grow.ui.main.home.HomeScreen
import com.molohala.grow.ui.main.profile.ProfileScreen
import com.molohala.grow.ui.root.AppViewModel

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
            is BottomTabItemType.Home -> HomeScreen(
                navController = navController,
                appViewModel = appViewModel
            )

            is BottomTabItemType.Forum -> ForumScreen(
                navController = navController,
                appViewModel = appViewModel
            )

            is BottomTabItemType.Github -> GithubRankScreen(
                navController = navController,
                appViewModel = appViewModel
            )

            is BottomTabItemType.Baekjoon -> BaekjoonRankScreen(
                navController = navController,
                appViewModel = appViewModel
            )

            is BottomTabItemType.Profile -> ProfileScreen(
                navController = navController,
                appViewModel = appViewModel
            )
        }
        Column {
            Spacer(modifier = Modifier.weight(1f))
            GrowBottomTabBar(
                modifier = Modifier,
                selected = uiAppState.selectedTab
            ) {
                appViewModel.clickTab(it)
            }
        }
    }
}