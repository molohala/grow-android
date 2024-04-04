package com.molohala.infinity

import androidx.annotation.RawRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController()
) {

    var viewType by remember {
        mutableStateOf<MainViewType>(MainViewType.Home)
    }

    val mainViews = arrayListOf(
        MainViewType.Home,
        MainViewType.Community,
        MainViewType.GithubRank,
        MainViewType.Baekjoon,
        MainViewType.Profile,
    )

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .drawColoredShadow(
                        color = Color.Black,
                        alpha = 0.04f,
                        offsetY = 0.dp,
                        shadowRadius = 10.dp
                    )
                    .background(Color.White)
                    .padding(vertical = 12.dp)
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                mainViews.forEach { view ->
                    Column(
                        modifier = Modifier
                            .bounceClick(onClick = {
                                viewType = view
                            }),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(28.dp),
                            painter = painterResource(id = view.icon),
                            contentDescription = view.name,
                            tint = if (viewType == view) Color(0xFF2279FF) else Color.LightGray
                        )
                        Text(
                            modifier = Modifier,
                            text = view.name,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
        ) {
            when (viewType) {
                is MainViewType.Home -> Text(text = "home")
                is MainViewType.Community -> CommunityScreen(navController = navController)
                MainViewType.GithubRank -> GithubRankScreen(navController = navController)
                MainViewType.Baekjoon -> Text(text = "baekjoon")
                MainViewType.Profile -> ProfileScreen(navController = navController)
            }
        }
    }
}

sealed class MainViewType(
    val name: String,
    val icon: Int
) {
    data object Home : MainViewType(name = "홈", icon = R.drawable.ic_home)
    data object Community : MainViewType(name = "커뮤니티", icon = R.drawable.ic_community)
    data object GithubRank : MainViewType(name = "Github", icon = R.drawable.ic_github)
    data object Baekjoon : MainViewType(name = "백준", icon = R.drawable.ic_baekjoon)
    data object Profile : MainViewType(name = "프로필", icon = R.drawable.ic_profile)
}