package com.molohala.infinity

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainNavigationGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "profile"
    ) {
        composable("community") {
            CommunityScreen(navController = navController)
        }
        composable("github_rank") {
            GithubRankScreen(navController = navController)
        }
        composable("profile") {
            ProfileScreen(navController = navController)
        }
    }
}