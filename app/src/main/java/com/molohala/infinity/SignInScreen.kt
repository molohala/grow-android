package com.molohala.infinity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SignInScreen(

) {
    Column {

        Spacer(modifier = Modifier.weight(1f))
        InfinityButton(
            modifier = Modifier
                .padding(bottom = 24.dp)
                .padding(horizontal = 16.dp),
            text = "도담도담 로그인"
        ) {
            // handle sign in
        }
    }
}