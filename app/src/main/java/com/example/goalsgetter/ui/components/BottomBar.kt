package com.example.goalsgetter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.goalsgetter.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.goalsgetter.ui.theme.White

@Composable
fun BottomBar(navController: NavController) {
    NavigationBar (
        containerColor = Color.White,
    ){
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(R.drawable.dashboard_ic),
                    contentDescription = "Dashboard",
                    modifier = Modifier.size(34.dp) // Set size to 34x34
                )
            },
            label = { Text("Dashboard") },
            selected = navController.currentDestination?.route == "dashboard",
            onClick = { navController.navigate("dashboard") {
                popUpTo("dashboard") { inclusive = true }
            } }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(R.drawable.acivity_ic),
                    contentDescription = "Routine",
                    modifier = Modifier.size(34.dp) // Set size to 34x34
                )
            },
            label = { Text("Routine") },
            selected = navController.currentDestination?.route == "routine",
            onClick = { navController.navigate("routine") {
                popUpTo("routine") { inclusive = true }
            } }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(R.drawable.settind_ic),
                    contentDescription = "Profile",
                    modifier = Modifier.size(34.dp) // Set size to 34x34
                )
            },
            label = { Text("Setting") },
            selected = navController.currentDestination?.route == "profile",
            onClick = { navController.navigate("setting") {
                popUpTo("setting") { inclusive = true }
            } }
        )
    }
}