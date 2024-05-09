package com.molohala.grow.ui.main.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.molohala.grow.ui.main.createforum.CreateForumScreen
import com.molohala.grow.ui.main.editforum.EditForumScreen
import com.molohala.grow.ui.main.forumdetail.ForumDetailScreen
import com.molohala.grow.ui.main.profile.setting.SettingScreen
import com.molohala.grow.ui.main.profile.setting.baekjoonsetting.BaekjoonSettingScreen
import com.molohala.grow.ui.main.profile.setting.githubsetting.GithubSettingScreen
import com.molohala.grow.ui.main.profile.setting.profileedit.ProfileEditScreen
import com.molohala.grow.ui.main.profiledetail.ProfileDetailScreen
import com.molohala.grow.ui.root.AppViewModel
import com.molohala.grow.ui.signin.SignInScreen

@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
    appViewModel: AppViewModel
) {
    val uiState by appViewModel.uiState.collectAsState()
    NavHost(
        navController = navController,
        startDestination = getStartDestination(isAuthorization = uiState.accessToken.isEmpty())
    ) {
        composable(NavGroup.SignIn.name) {
            SignInScreen(navController = navController, appViewModel = appViewModel)
        }
        composable(NavGroup.Main.name) {
            MainScreen(navController = navController, appViewModel = appViewModel)
        }
        composable(
            route = "${NavGroup.ProfileDetail.name}/{${NavGroup.ProfileDetail.MEMBER_ID}}",
            arguments = listOf(navArgument(NavGroup.ProfileDetail.MEMBER_ID) {
                type = NavType.IntType
                nullable = false
            })
        ) { entry ->
            val memberId = entry.arguments?.getInt(NavGroup.ProfileDetail.MEMBER_ID, 0) ?: 0
            ProfileDetailScreen(
                navController = navController,
                memberId = memberId
            )
        }
        composable(NavGroup.Setting.name) {
            SettingScreen(navController = navController, appViewModel = appViewModel)
        }
        composable(NavGroup.ProfileEdit.name) {
            ProfileEditScreen(navController = navController, appViewModel = appViewModel)
        }
        composable(NavGroup.GithubSetting.name) {
            GithubSettingScreen(navController = navController, appViewModel = appViewModel)
        }
        composable(NavGroup.BaekjoonSetting.name) {
            BaekjoonSettingScreen(navController = navController, appViewModel = appViewModel)
        }
        composable(NavGroup.CreateForum.name) {
            CreateForumScreen(navController = navController)
        }
        composable(
            route = "${NavGroup.ForumDetail.name}/{${NavGroup.ForumDetail.FORUM_ID}}",
            arguments = listOf(navArgument(NavGroup.ForumDetail.FORUM_ID) {
                type = NavType.IntType
                nullable = false
            })
        ) { entry ->
            val forumId = entry.arguments?.getInt(NavGroup.ForumDetail.FORUM_ID, 0) ?: 0
            ForumDetailScreen(
                navController = navController,
                appViewModel = appViewModel,
                forumId = forumId
            )
        }
        composable(
            route = "${NavGroup.EditForum.name}/{${NavGroup.EditForum.FORUM_ID}}",
            arguments = listOf(navArgument(NavGroup.EditForum.FORUM_ID) {
                type = NavType.IntType
                nullable = false
            })
        ) { entry ->
            val forumId = entry.arguments?.getInt(NavGroup.ForumDetail.FORUM_ID, 0) ?: 0
            EditForumScreen(navController = navController, forumId = forumId)
        }
    }
}

@Composable
fun getStartDestination(isAuthorization: Boolean) =
    if (isAuthorization) NavGroup.SignIn.name else NavGroup.Main.name