package com.molohala.infinity.ui.main

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.molohala.infinity.ui.main.profile.ProfileScreen
import com.molohala.infinity.R
import com.molohala.infinity.extension.bounceClick
import com.molohala.infinity.extension.drawColoredShadow
import com.molohala.infinity.ui.main.community.CommunityScreen
import com.molohala.infinity.ui.main.githubrank.GithubRankScreen

@Composable
fun MainScreen(
    navController: NavController
) {

    var viewType by remember {
        mutableStateOf<BottomNavigationType>(BottomNavigationType.Home)
    }

    val mainViews = arrayListOf(
        BottomNavigationType.Home,
        BottomNavigationType.Community,
        BottomNavigationType.GithubRank,
        BottomNavigationType.Baekjoon,
        BottomNavigationType.Profile,
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
                    .padding(vertical = 12.dp, horizontal = 12.dp)
                    .padding(bottom = 16.dp)
            ) {
                mainViews.forEach { view ->
                    Column(
                        modifier = Modifier
                            .weight(1f)
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
                            color = if (viewType == view) Color(0xFF2279FF) else Color.Gray
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
                is BottomNavigationType.Home -> Text(text = "home")
                is BottomNavigationType.Community -> CommunityScreen(navController = navController)
                BottomNavigationType.GithubRank -> GithubRankScreen(navController = navController)
                BottomNavigationType.Baekjoon -> Text(text = "baekjoon")
                BottomNavigationType.Profile -> ProfileScreen(navController = navController)
            }
        }
    }
}

sealed class BottomNavigationType(
    val name: String,
    val icon: Int
) {
    data object Home : BottomNavigationType(name = "홈", icon = R.drawable.ic_home)
    data object Community : BottomNavigationType(name = "커뮤니티", icon = R.drawable.ic_community)
    data object GithubRank : BottomNavigationType(name = "Github", icon = R.drawable.ic_github)
    data object Baekjoon : BottomNavigationType(name = "백준", icon = R.drawable.ic_baekjoon)
    data object Profile : BottomNavigationType(name = "프로필", icon = R.drawable.ic_profile)
}