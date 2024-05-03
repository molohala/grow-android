package com.molohala.grow.ui.main.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.molohala.grow.ui.main.createcommunity.CreateCommunityScreen
import com.molohala.grow.ui.main.profile.setting.SettingScreen
import com.molohala.grow.ui.main.profile.setting.baekjoonsetting.BaekjoonSettingScreen
import com.molohala.grow.ui.main.profiledetail.ProfileDetailScreen
import com.molohala.grow.ui.main.profile.setting.githubsetting.GithubSettingScreen
import com.molohala.grow.ui.main.profile.setting.profileedit.ProfileEditScreen
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
        composable(NavGroup.ProfileDetail.name) {
            ProfileDetailScreen(navController = navController)
        }
        composable(NavGroup.Setting.name) {
            SettingScreen(navController = navController, appViewModel = appViewModel)
        }
        composable(NavGroup.ProfileEdit.name) {
            ProfileEditScreen(navController = navController)
        }
        composable(NavGroup.GithubSetting.name) {
            GithubSettingScreen(navController = navController)
        }
        composable(NavGroup.BaekjoonSetting.name) {
            BaekjoonSettingScreen(navController = navController)
        }
        composable(NavGroup.CreateCommunity.name) {
            CreateCommunityScreen(navController = navController)
        }
        composable(NavGroup.CommunityDetail.name) {

        }
    }
}

@Composable
fun getStartDestination(isAuthorization: Boolean) =
    if (isAuthorization) NavGroup.SignIn.name else NavGroup.Main.name