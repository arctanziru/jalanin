package com.example.goalsgetter.features.dashboard.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.goalsgetter.ui.components.BottomBar
import com.example.goalsgetter.ui.components.CustomCard
import com.example.goalsgetter.ui.theme.Primary

@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val greeting by viewModel.greeting.collectAsState()
    val motivationQuoteState by viewModel.motivationQuote.collectAsState()
    val activeRoutineState by viewModel.activeRoutine.collectAsState()

    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GreetingSection(greeting)

            MotivationQuoteSection(motivationQuoteState)

            ActiveRoutineSection(activeRoutineState)
        }
    }
}

@Composable
fun GreetingSection(greeting: String) {
    CustomCard(containerColor = Primary) {
        Text(
            text = greeting,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
fun MotivationQuoteSection(motivationQuoteState: MotivationQuote) {
    CustomCard() {
        when {
            motivationQuoteState.quote.isNotEmpty() -> {
                Text(
                    text = motivationQuoteState.quote,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            motivationQuoteState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            motivationQuoteState.errorMessage != null -> {
                Text(
                    text = motivationQuoteState.errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun ActiveRoutineSection(activeRoutineState: ActiveRoutine) {
    CustomCard() {
        when {
            activeRoutineState.routine != null -> {
                Text(
                    text = "Active Routine: ${activeRoutineState.routine.title}",
                    style = MaterialTheme.typography.headlineSmall
                )
                activeRoutineState.routine.activities.forEach { activity ->
                    Text(
                        text = "- ${activity.title} (${if (activity.completed) "Completed" else "Pending"})",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            activeRoutineState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            activeRoutineState.errorMessage != null -> {
                Text(
                    text = activeRoutineState.errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            else -> {
                Text(text = "No active routine found.")
            }
        }
    }
}
