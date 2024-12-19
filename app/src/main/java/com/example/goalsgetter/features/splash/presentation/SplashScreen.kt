package com.example.goalsgetter.features.splash.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.goalsgetter.R

@Composable
fun SplashScreen(navController: NavController, viewModel: SplashViewModel = hiltViewModel()) {
    val navigationState by viewModel.navigation.collectAsState()

    LaunchedEffect(navigationState) {
        when (navigationState) {
            SplashNavigation.ToDashboard -> {
                navController.navigate("dashboard") {
                    popUpTo(navController.graph.id) { inclusive = true }

                }
            }
            SplashNavigation.ToLogin -> {
                navController.navigate("login") {
                    popUpTo(navController.graph.id) { inclusive = true }
                }
            }
            null -> {}
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // Centers the content inside the Box
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // Centers children horizontally
            verticalArrangement = Arrangement.Center,          // Centers children vertically
            modifier = Modifier.fillMaxSize()                  // Makes Column take full screen space
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Goals Getter",
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }


}
