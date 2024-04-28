package com.molohala.infinity.ui.main.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.molohala.infinity.application.InfinityApp
import com.molohala.infinity.ui.main.profile.setting.SettingScreen
import com.molohala.infinity.ui.main.profile.setting.baekjoonsetting.BaekjoonSettingScreen
import com.molohala.infinity.ui.main.profiledetail.ProfileDetailScreen
import com.molohala.infinity.ui.main.profile.setting.githubsetting.GithubSettingScreen
import com.molohala.infinity.ui.main.profile.setting.profileedit.ProfileEditScreen
import com.molohala.infinity.ui.signin.SignInScreen

@Composable
fun MainNavigationGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = getStartDestination()
    ) {
        composable(NavGroup.SignIn.name) {
            SignInScreen(navController = navController)
        }
        composable(NavGroup.Main.name) {
            MainScreen(navController = navController)
        }
        composable(NavGroup.ProfileDetail.name) {
            ProfileDetailScreen(navController = navController)
        }
        composable(NavGroup.Setting.name) {
            SettingScreen(navController = navController)
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
    }
}

fun getStartDestination() = if (InfinityApp.prefs.accessToken.isEmpty()) NavGroup.SignIn.name else NavGroup.Main.name