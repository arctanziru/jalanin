package com.example.goalsgetter.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.goalsgetter.core.navigation.AppNavGraph
import com.example.goalsgetter.ui.theme.GoalsGetterTheme
import com.example.goalsgetter.ui.theme.Light
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoalsGetterTheme {
                val navController = rememberNavController()
                Surface(color = Light) {
                    AppNavGraph(navController = navController)
                }
            }
        }
    }
}
