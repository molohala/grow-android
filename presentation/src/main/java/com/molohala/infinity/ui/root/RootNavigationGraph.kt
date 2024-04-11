package com.molohala.infinity.ui.root

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.molohala.infinity.ui.main.main.MainNavigationGraph
import com.molohala.infinity.ui.signin.SignInScreen

@Composable
fun RootNavigationGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = RootViewType.Main.name) {
        composable(RootViewType.SignIn.name) {
            SignInScreen()
        }
        composable(RootViewType.Main.name) {
            MainNavigationGraph()
        }
    }
}