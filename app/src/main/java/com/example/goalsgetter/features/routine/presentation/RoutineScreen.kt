package com.example.goalsgetter.features.routine.presentation

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.goalsgetter.core.navigation.Screen
import com.example.goalsgetter.features.routine.common.RoutineActionDialog
import com.example.goalsgetter.features.routine.data.Activity
import com.example.goalsgetter.features.routine.data.Routine
import com.example.goalsgetter.ui.components.AppBar
import com.example.goalsgetter.ui.components.AppBarVariant
import com.example.goalsgetter.ui.components.BottomBar
import com.example.goalsgetter.ui.components.CustomCard
import com.example.goalsgetter.ui.components.PaddingMode
import com.example.goalsgetter.ui.theme.Dark
import com.example.goalsgetter.ui.theme.White

@Composable
fun RoutineScreen(navController: NavController, viewModel: RoutineViewModel = hiltViewModel()) {
    val routinesState by viewModel.routinesState.collectAsState()
    val dialogState by viewModel.dialogState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(routinesState.errorMessage) {
        routinesState.errorMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.RoutineCreateEdit.route)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Routine")
            }
        },
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        Column(

        ){
            AppBar(
                title =  stringResource(R.string.appbarRoutine),
                variant = AppBarVariant.PLAIN,
                navController = navController,
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(24.dp, 12.dp)
                ){
                    Text(
                        stringResource(R.string.whatsYourGoal),
                        style = MaterialTheme.typography.headlineMedium,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold

                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                //Today Routine
                CustomCard {
                    Text(
                        stringResource(R.string.todayRoutine),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    if (routinesState.isLoading && routinesState.routines.isEmpty()) {
                        CircularProgressIndicator()
                    } else if (routinesState.errorMessage != null) {
                        Text(
                            text = routinesState.errorMessage ?: "",
                            color = MaterialTheme.colorScheme.error
                        )
                    } else {
                        routinesState.routines.find { it?.active == true }?.let { routine ->
                            Column() {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = routine.title,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontSize = 24.sp, // Use sp instead of dp
                                    fontWeight = FontWeight.Medium
                                )
                                Text(routine.description, style = MaterialTheme.typography.bodyMedium , fontSize = 16.sp)
                                Text(stringResource(R.string.activity), style = MaterialTheme.typography.bodyMedium , fontSize = 14.sp, fontWeight = FontWeight.Medium)
                                Spacer(modifier = Modifier.height(8.dp))

                                routine.activities.forEach { activity ->
                                    ActivityItem(
                                        activity,
                                        onToggleCompleted = { activityId, isCompleted ->
                                            viewModel.toggleActivityCompleted(
                                                routine.id,
                                                activityId,
                                                isCompleted
                                            )
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))

                                }
                            }
                        } ?: Text(stringResource(R.string.noActiveRoutine))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                //All Routine
                CustomCard(paddingMode = PaddingMode.VERTICAL) {
                    Row(modifier=Modifier.padding(24.dp ,0.dp)) {
                        Text(
                            stringResource(R.string.routineList),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyColumn {
                        if (routinesState.isLoading && routinesState.routines.isEmpty()) {
                            item {
                                Column(modifier = Modifier.padding(24.dp,0.dp)){
                                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                                }
                            }
                        } else if (routinesState.errorMessage != null) {
                            item {
                                Text(
                                    text = routinesState.errorMessage ?: "",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        } else if (routinesState.routines.isEmpty()) {
                            item {
                                Column(modifier=Modifier.padding(24.dp,0.dp)){
                                    Text(text=stringResource(R.string.noRoutines))
                                }
                            }
                        } else {
                            val nonNullRoutines = routinesState.routines.filterNotNull()
                            items(nonNullRoutines) { routine ->
                                RoutineListItem(
                                    routine = routine,
                                    onClick = { viewModel.showDialog(routine) }
                                )
                            }
                        }
                    }

                    dialogState?.let { routine ->
                        RoutineActionDialog(
                            routine = routine,
                            onEditClick = {
                                viewModel.hideDialog()
                                navController.navigate(Screen.RoutineCreateEdit.createRoute(routine.id))
                            },
                            onActiveClick = {
                                viewModel.toggleActiveRoutine(routine.id, !routine.active)
                                viewModel.hideDialog()
                            },
                            onDismiss = viewModel::hideDialog,
                            active = routine.active,
                            onDeleteClick = {
                                viewModel.deleteRoutine(routine.id)
                                viewModel.hideDialog()
                            }
                        )
                    }
                }

            }
        }

    }
}

@Composable
fun ActivityItem(
    activity: Activity,
    onToggleCompleted: (String, Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(activity.title, style = MaterialTheme.typography.bodyMedium)
            Text(activity.description, style = MaterialTheme.typography.bodySmall)
        }
        Checkbox(
            checked = activity.completed, // Correctly reflects only this activity
            onCheckedChange = { isChecked ->
                onToggleCompleted(activity.id, isChecked) // Pass the activity.id for specific updates
            }
        )
    }
}


@SuppressLint("InvalidColorHexValue")
@Composable
fun RoutineListItem(routine: Routine, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = if (routine.active) Color(0xFFE7E7E7) else White)
    ) {
        Column(modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp)) {
            Text(text = routine.title, style = MaterialTheme.typography.headlineSmall, fontSize = 20.sp, fontWeight = FontWeight.Medium)
            Text(text = routine.description, style = MaterialTheme.typography.bodyMedium, fontSize = 14.sp)
        }
    }
}
