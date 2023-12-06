package com.pouyaa.takehomegithubapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.pouyaa.takehomegithubapi.feature.home.navigation.homeNavigationRoute
import com.pouyaa.takehomegithubapi.navigation.TakeHomeNavHost
import com.pouyaa.takehomegithubapi.ui.theme.TakeHomeGithubAPITheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            TakeHomeGithubAPITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TakeHomeNavHost(
                        navController = navController,
                        startDestination = homeNavigationRoute
                    )
                }
            }
        }
    }
}