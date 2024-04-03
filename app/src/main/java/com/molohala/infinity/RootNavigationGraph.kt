package com.molohala.infinity

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun RootNavigationGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "signIn") {
        composable("signIn") {
            SignInScreen()
        }
    }
}