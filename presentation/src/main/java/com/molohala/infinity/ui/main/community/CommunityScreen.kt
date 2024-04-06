package com.molohala.infinity.ui.main.community

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.molohala.infinity.typo.Title

@Composable
fun CommunityScreen(
    navController: NavController
) {

    val tempArr = Array(30) { it }

    LazyColumn(
        modifier = Modifier
            .background(Color(0xFFF4F5F9))
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Title(
                modifier = Modifier,
                text = "커뮤니티"
            )
        }
        items(tempArr) {
            CommunityCell()
        }
    }
}