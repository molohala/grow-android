package com.molohala.infinity.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.molohala.infinity.ProfileDetailScreen
import com.molohala.infinity.ProfileEditScreen

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
        composable("profile_edit") {
            ProfileEditScreen(navController = navController)
        }
    }
}
