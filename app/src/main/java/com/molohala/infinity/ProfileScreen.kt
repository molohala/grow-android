package com.molohala.infinity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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

@Composable
fun ProfileScreen(
    navController: NavController
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .background(Color(0xFFF4F5F9))
            .padding(horizontal = 16.dp)
            .fillMaxHeight()
            .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Title(
            text = "프로필"
        )
        Profile {
        }
        InfinityGithubCell {

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
            .bounceClick(onClick = onClick)
            .applyCardView(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
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
            Icon(
                modifier = Modifier
                    .size(18.dp),
                painter = painterResource(id = R.drawable.ic_expand_right),
                contentDescription = null,
                tint = Color.Gray
            )
        }

        Text(
            modifier = Modifier
                .padding(vertical = 24.dp),
            text = "\"뚝딱뚝딱\"",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Gray
        )
    }
}