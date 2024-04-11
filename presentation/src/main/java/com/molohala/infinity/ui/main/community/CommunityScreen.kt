package com.molohala.infinity.ui.main.community

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.molohala.infinity.color.InfinityColor
import com.molohala.infinity.commnuity.InfinityCommunityCell
import com.molohala.infinity.extension.bounceClick
import com.molohala.infinity.icon.IconAdd
import com.molohala.infinity.typo.TopBar

@Composable
fun CommunityScreen(
    navController: NavController
) {

    val tempArr = Array(30) { it }

    TopBar(
        modifier = Modifier,
        backgroundColor = InfinityColor.background,
        text = "포럼"
    ) {
        Box {
            LazyColumn(
                modifier = Modifier
                    .background(Color(0xFFF4F5F9))
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tempArr) {
                    InfinityCommunityCell()
                }
                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
            Row(
                modifier = Modifier
                    .padding(end = 24.dp)
                    .padding(bottom = 24.dp)
                    .bounceClick(onClick = {

                    })
                    .align(Alignment.BottomEnd)
                    .height(48.dp)
                    .clip(CircleShape)
                    .background(InfinityColor.blue)
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconAdd()
                Text(
                    modifier = Modifier
                        .padding(end = 4.dp),
                    text = "글쓰기",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }

}
