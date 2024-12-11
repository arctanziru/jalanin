package com.example.goalsgetter.features.splash.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun SplashScreen(navController: NavController, viewModel: SplashViewModel = hiltViewModel()) {
    val navigationState by viewModel.navigation.collectAsState()

    LaunchedEffect(navigationState) {
        when (navigationState) {
            SplashNavigation.ToDashboard -> {
                navController.navigate("dashboard") {
                    popUpTo("splash") { inclusive = true }
                }
            }
            SplashNavigation.ToLogin -> {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            }
            null -> {}
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Goals Getter", style = MaterialTheme.typography.headlineLarge)
    }

}
