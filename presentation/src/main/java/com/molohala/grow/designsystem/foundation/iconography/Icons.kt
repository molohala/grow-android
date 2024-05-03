package com.molohala.grow.icon

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.molohala.grow.R


@Composable
fun IconRightArrow() {
    Icon(
        modifier = Modifier
            .size(18.dp),
        painter = painterResource(id = R.drawable.ic_expand_right),
        contentDescription = null,
        tint = Color.Gray
    )
}

@Composable
fun IconLogout() {
    Icon(
        modifier = Modifier
            .size(22.dp),
        painter = painterResource(id = R.drawable.ic_logout),
        contentDescription = null,
        tint = Color.Red
    )
}

@Composable
fun IconAdd() {
    Icon(
        modifier = Modifier
            .size(22.dp),
        painter = painterResource(id = R.drawable.ic_add),
        contentDescription = null,
        tint = Color.White
    )
}