package com.molohala.grow.ui.root

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.bestswlkh0310.mydesignsystem.foundation.MyTheme
import com.bestswlkh0310.mydesignsystem.foundation.color.MyPallete
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.molohala.grow.application.GrowApp
import com.molohala.grow.application.PreferenceManager
import com.molohala.grow.common.constant.TAG
import com.molohala.grow.ui.main.main.NavigationGraph

class AppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        GrowApp.prefs = PreferenceManager(applicationContext)
        checkPermission()
        setContent {
            val appViewModel: AppViewModel = viewModel()
            val navController = rememberNavController()
            val uiState by appViewModel.uiState.collectAsState()

            MyTheme(
                darkTheme = uiState.isDarkMode
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    NavigationGraph(
                        navController = navController,
                        appViewModel = appViewModel
                    )
                }
            }
        }
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            TedPermission.create()
                .setPermissions(Manifest.permission.POST_NOTIFICATIONS)
                .setPermissionListener(object : PermissionListener {
                    override fun onPermissionGranted() {
                        Log.d(TAG, "onPermissionGranted: ")
                    }
                    override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                        Log.d(TAG, "onPermissionDenied: ")
                    }
                })
                .check()
        }
    }
}
