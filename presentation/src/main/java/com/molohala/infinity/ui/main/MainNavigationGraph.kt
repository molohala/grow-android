package com.molohala.infinity.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.molohala.infinity.ui.main.profile.setting.SettingScreen
import com.molohala.infinity.ui.main.profiledetail.ProfileDetailScreen
import com.molohala.infinity.ui.main.profile.setting.profileedit.ProfileEditScreen

@Composable
fun MainNavigationGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = MainViewType.Main.name
    ) {
        composable(MainViewType.Main.name) {
            MainScreen(navController = navController)
        }
        composable(MainViewType.ProfileDetail.name) {
            ProfileDetailScreen(navController = navController)
        }
        composable(MainViewType.ProfileEdit.name) {
            ProfileEditScreen(navController = navController)
        }
        composable(MainViewType.Setting.name) {
            SettingScreen(navController = navController)
        }
        composable(MainViewType.GithubSetting.name) {
            ProfileEditScreen(navController = navController)
        }
        composable(MainViewType.BaekjoonSetting.name) {
            ProfileEditScreen(navController = navController)
        }
    }
}
