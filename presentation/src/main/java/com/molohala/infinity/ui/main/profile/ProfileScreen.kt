package com.molohala.infinity.ui.main.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.molohala.infinity.baekjoon.InfinityBaekjoonCell
import com.molohala.infinity.github.InfinityGithubCell
import com.molohala.infinity.R
import com.molohala.infinity.color.InfinityColor
import com.molohala.infinity.typo.Title
import com.molohala.infinity.extension.applyCardView
import com.molohala.infinity.extension.bounceClick
import com.molohala.infinity.ui.main.MainViewType

@Composable
fun ProfileScreen(
    navController: NavController
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .background(InfinityColor.background)
            .padding(horizontal = 16.dp)
            .fillMaxHeight()
            .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Title(
            text = "프로필"
        )
        Profile {
            navController.navigate(MainViewType.Setting.name)
        }
        InfinityGithubCell {

        }
        InfinityBaekjoonCell {

        }
        Logout {

        }
    }
}

@Composable
fun Profile(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .applyCardView(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
            Text(
                modifier = Modifier
                    .padding(start = 8.dp),
                text = "노영재",
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(InfinityColor.background)
                    .padding(vertical = 8.dp, horizontal = 12.dp)
                    .bounceClick(onClick = onClick),
                text = "설정",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
        }

        Text(
            modifier = Modifier
                .padding(vertical = 24.dp),
            text = "\"뚝딱뚝딱\"",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
    }
}

@Composable
fun Logout(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .bounceClick(onClick = onClick)
            .applyCardView(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "로그아웃",
            color = Color.Red,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier
                .size(18.dp),
            painter = painterResource(id = R.drawable.ic_logout),
            contentDescription = null,
            tint = Color.Red
        )
    }
}