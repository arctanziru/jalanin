package com.example.goalsgetter.features.setting.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.goalsgetter.R
import com.example.goalsgetter.ui.components.AppBar
import com.example.goalsgetter.ui.components.AppBarVariant
import com.example.goalsgetter.ui.components.BottomBar

@Composable
fun SettingsScreen(navController: NavController, viewModel: SettingsViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val showLanguageDialog = remember { mutableStateOf(false) }

    val fullName = viewModel.fullName.collectAsState().value
    val language = viewModel.language.collectAsState().value

    LaunchedEffect(language) {}

    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->

        Column() {
            AppBar(
                title = stringResource(R.string.appbarSetting),
                variant = AppBarVariant.PLAIN,
                navController = navController,
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center // Centers content horizontally
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally // Centers content within the column

                    ) {

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(120.dp) // Circle size
                                .background(
                                    Color(0xFF26A69A),
                                    shape = CircleShape
                                ) // Circle shape with color
                        ) {
                            Text(
                                text = fullName?.firstOrNull()?.uppercaseChar()?.toString()
                                    ?: "G", // Avatar letter
                                fontSize = 48.sp, // Letter size
                                fontWeight = FontWeight.Bold,
                                color = Color.White // Letter color
                            )
                        }

                        Spacer(Modifier.height(10.dp))
                        Text(
                            text = fullName?.trim() ?: "Guest", // Show fallback if null
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                }
                Spacer(modifier = Modifier.height(32.dp))
                Card(
                    modifier = Modifier
                        .padding(horizontal = 0.dp) // No extra horizontal padding
                        .fillMaxWidth() // Ensures the Card spans the full width
                ) {
                    Column {
                        ListItem(
                            headlineContent = {
                                Text(
                                    text = stringResource(R.string.settingLanguage),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Normal

                                )
                            },
                            trailingContent = {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth() // Forces ListItem to take full width
                                .background(Color.White)
                                .clickable { showLanguageDialog.value = true }
                                .padding(horizontal = 12.dp, vertical = 12.dp),
                            colors = ListItemDefaults.colors(
                                containerColor = Color.Transparent
                            )
                        )
                        ListItem(
                            headlineContent = {
                                Text(
                                    text = "Log Out",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Normal

                                )
                            },
                            trailingContent = {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth() // Forces ListItem to take full width
                                .background(Color.White)
                                .clickable {
                                    viewModel.logout {
                                        navController.navigate("login") {
                                            popUpTo(navController.graph.id) { inclusive = true }
                                        }
                                    }
                                }
                                .padding(horizontal = 12.dp, vertical = 12.dp),
                            colors = ListItemDefaults.colors(
                                containerColor = Color.Transparent
                            )
                        )
                    }
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
}

@Composable
fun LanguageSelectionDialog(
    onDismiss: () -> Unit,
    onLanguageSelected: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.langChoose)) },
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
