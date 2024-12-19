package com.example.goalsgetter.features.dashboard.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.goalsgetter.ui.components.CustomCard
import com.example.goalsgetter.ui.theme.Primary

@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val motivationQuoteState by viewModel.motivationQuote.collectAsState()
    val activeRoutineState by viewModel.activeRoutine.collectAsState()
    val fullName by viewModel.fullName.collectAsState()

    Scaffold(

        bottomBar = {
            BottomBar(navController)
        }
    ) { innerPadding ->
        Column(
        ) {
            AppBar(
                title = "Goals Getter",
                variant = AppBarVariant.PLAIN,
                navController = navController,
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    GreetingSection("${stringResource(R.string.greeting)}, ${fullName?.trim()?.split(" ")?.first() ?: "Guest"}")
                }

                item {
                    MotivationQuoteSection(motivationQuoteState)
                }

                item {
                    ActiveRoutineSection(activeRoutineState)
                }

            }
        }
    }
}

@Composable
fun GreetingSection(greeting: String) {
    CustomCard(containerColor = Primary) {
        Column() {
            Text(
                text = greeting,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 20.sp, // First text size
                    fontWeight = FontWeight.Normal, // Regular weight
                    color = Color.White // White text
                )
            )
            Spacer(modifier = Modifier.height(4.dp)) // 4dp spacing
            Text(
                stringResource(R.string.howYourDay),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 24.sp, // Second text size
                    fontWeight = FontWeight.SemiBold, // SemiBold weight
                    color = Color.White // White text
                )
            )
        }
    }
}

@Composable
fun MotivationQuoteSection(motivationQuoteState: MotivationQuote) {
    CustomCard() {
        when {
            motivationQuoteState.quote.isNotEmpty() -> {
                Column() {

                    Text(

                        stringResource(R.string.motivationQuotes),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = motivationQuoteState.quote,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                    )

                }
            }

            motivationQuoteState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            motivationQuoteState.errorMessage != null -> {
                Text(
                    text = "\"${motivationQuoteState.quote}\"", // Wrap in quotes
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
                    text = activeRoutineState.routine.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium

                )
                Text(
                    text = activeRoutineState.routine.description,
                    style = MaterialTheme.typography.headlineSmall,
                    fontSize = 16.sp, fontWeight = FontWeight.Light
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column {
                    activeRoutineState.routine.activities.forEach { activity ->
                        Text(
                            text = "${activity.title} (${stringResource(if (activity.completed) R.string.completed else R.string.pending)})",
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 16.sp, fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = activity.description,
                            style = MaterialTheme.typography.bodyMedium, fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
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
                Text(text = stringResource(R.string.noActiveRoutine))
            }
        }
    }
}
