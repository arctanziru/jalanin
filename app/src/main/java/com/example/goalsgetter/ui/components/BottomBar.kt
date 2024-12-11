package com.example.goalsgetter.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.goalsgetter.R

@Composable
fun BottomBar(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(painterResource(R.drawable.ic_launcher_foreground), contentDescription = "Dashboard") },
            label = { Text("Dashboard") },
            selected = navController.currentDestination?.route == "dashboard",
            onClick = { navController.navigate("dashboard") {
                popUpTo("dashboard") { inclusive = true }
            } }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(R.drawable.ic_launcher_foreground), contentDescription = "Routine") },
            label = { Text("Routine") },
            selected = navController.currentDestination?.route == "routine",
            onClick = { navController.navigate("routine") {
                popUpTo("routine") { inclusive = true }
            } }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(R.drawable.ic_launcher_foreground), contentDescription = "Profile") },
            label = { Text("Setting") },
            selected = navController.currentDestination?.route == "profile",
            onClick = { navController.navigate("setting") {
                popUpTo("setting") { inclusive = true }
            } }
        )
    }
}
