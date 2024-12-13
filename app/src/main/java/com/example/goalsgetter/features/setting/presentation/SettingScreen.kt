package com.example.goalsgetter.features.setting.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.goalsgetter.ui.components.BottomBar
import com.example.goalsgetter.ui.components.CustomCard

@Composable
fun SettingsScreen(navController: NavController, viewModel: SettingsViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val showLanguageDialog = remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // User profile
            Spacer(modifier = Modifier.height(32.dp))
            CustomCard {
                ListItem(
                    headlineContent = { Text("Bahasa") },
                    trailingContent = {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                    },
                    modifier = Modifier.clickable { showLanguageDialog.value = true }
                )
            }

            // About App Option
            CustomCard {
                ListItem(
                    headlineContent = { Text("Log Out") },
                    trailingContent = {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                    },
                    modifier = Modifier.clickable {
                        navController.navigate("login") {
                            popUpTo("splash") { inclusive = true }
                        }

                    }
                )
            }

            if (showLanguageDialog.value) {
                LanguageSelectionDialog(
                    onDismiss = { showLanguageDialog.value = false },
                    onLanguageSelected = { langCode ->
                        viewModel.changeLanguage(langCode, context)
                        showLanguageDialog.value = false
                    }
                )
            }
        }
    }
}

@Composable
fun LanguageSelectionDialog(
    onDismiss: () -> Unit,
    onLanguageSelected: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Pilih Bahasa") },
        text = {
            Column {
                TextButton(
                    onClick = { onLanguageSelected("id") } // Indonesian locale
                ) {
                    Text("Bahasa Indonesia")
                }
                TextButton(
                    onClick = { onLanguageSelected("en") } // English locale
                ) {
                    Text("English")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Tutup")
            }
        }
    )
}
