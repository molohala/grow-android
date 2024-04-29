package com.molohala.infinity.ui.root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.molohala.infinity.application.InfinityApp
import com.molohala.infinity.application.PreferenceManager
import com.molohala.infinity.ui.main.main.NavigationGraph
import com.molohala.infinity.ui.theme.InfinityTheme

class AppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InfinityApp.prefs = PreferenceManager(applicationContext)

        setContent {
            val appViewModel: AppViewModel = viewModel()
            val navController = rememberNavController()

            InfinityTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    NavigationGraph(
                        navController = navController,
                        appViewModel = appViewModel
                    )
                }
            }
        }
    }
}
