package com.example.goalsgetter.features.routine.presentation

import CustomTextField
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.goalsgetter.features.routine.data.Activity
import com.example.goalsgetter.ui.components.ButtonType
import com.example.goalsgetter.ui.components.CustomButton
import com.example.goalsgetter.ui.theme.Primary
import com.example.goalsgetter.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineCreateEditScreen(
    navController: NavController,
    routineId: String? = null,
    viewModel: RoutineCreateEditViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    var newActivityTitle by remember { mutableStateOf("") }
    var newActivityDescription by remember { mutableStateOf("") }

    val context = LocalContext.current
    val isEdit = routineId != null

    LaunchedEffect(routineId) {
        routineId?.let { viewModel.loadRoutine(it) }
    }

    LaunchedEffect(state.saveSuccess) {
        if (state.saveSuccess == true) {
            Toast.makeText(
                context,
                if (isEdit) context.getString(R.string.routineUpdated) else context.getString(R.string.routineCreated),
                Toast.LENGTH_SHORT
            ).show()
            navController.navigate("routine")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.background(color = Color.White),
                title = {
                    Text(
                        if (isEdit) stringResource(R.string.editRoutineBar) else stringResource(
                            R.string.createRoutineBar
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f) // Makes LazyColumn take up the remaining space
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    // Title and Description Fields
                    CustomTextField(
                        value = state.title,
                        onValueChange = viewModel::updateTitle,
                        label = stringResource(R.string.createRoutineName),
                        placeholder = stringResource(R.string.createRoutineNamePlaceholder),
                        modifier = Modifier.fillMaxWidth()
                    )

                    CustomTextField(
                        value = state.description,
                        onValueChange = viewModel::updateDescription,
                        label = stringResource(R.string.createRoutineDesc),
                        placeholder = stringResource(R.string.createRoutineDescPlaceholder),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        stringResource(R.string.activity),
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                // Activity List
                items(state.activities) { activity ->
                    ActivityItem(
                        activity = activity,
                        onRemove = { viewModel.removeActivity(it) }
                    )
                }

                item {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        CustomTextField(
                            value = newActivityTitle,
                            onValueChange = { newActivityTitle = it },
                            label = stringResource(R.string.createActivityName),
                            placeholder = stringResource(R.string.createActivityNamePlaceholder),
                            modifier = Modifier.fillMaxWidth()
                        )

                        CustomTextField(
                            value = newActivityDescription,
                            onValueChange = { newActivityDescription = it },
                            label = stringResource(R.string.createActivityDescription),
                            placeholder = stringResource(R.string.createActivityDescriptionPlaceholder),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(4.dp),
                            onClick = {
                                if (newActivityTitle.isNotBlank()) {
                                    viewModel.addActivity(
                                        Activity(
                                            title = newActivityTitle,
                                            description = newActivityDescription,
                                            completed = false
                                        )
                                    )
                                    newActivityTitle = ""
                                    newActivityDescription = ""
                                }
                            }
                        ) {
                            Text(stringResource(R.string.saveNewActivity))
                        }
                    }
                }

                item {

                    if (state.errorCode != ErrorCode.NONE) {
                        val errorMessage = when (state.errorCode) {
                            ErrorCode.BLANK_TITLE -> stringResource(R.string.errorBlankTitle)
                            ErrorCode.BLANK_DESCRIPTION -> stringResource(R.string.errorBlankDescription)
                            ErrorCode.NO_ACTIVITIES -> stringResource(R.string.errorNoActivities)
                            ErrorCode.GENERIC_ERROR -> stringResource(R.string.errorGeneric)
                            else -> ""
                        }
                        if (errorMessage.isNotEmpty()) {
                            Text(
                                text = errorMessage,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }


                item {
                    CustomButton(
                        color = Primary,
                        onClick = { viewModel.saveRoutine() },
                        text = if (state.isSaving) stringResource(R.string.savingRoutine) else if (isEdit) stringResource(
                            R.string.updateRoutine
                        ) else stringResource(R.string.saveRoutine),
                        textColor = MaterialTheme.colorScheme.onPrimary,
                        enabled = !state.isSaving,
                        buttonType = ButtonType.CONTAINED,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                }

            }
        }
    }
}

@Composable
fun ActivityItem(activity: Activity, onRemove: (Activity) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)  // This will make the column take up all available space
        ) {
            Text(activity.title, style = MaterialTheme.typography.bodyMedium, fontSize = 18.sp)
            Text(activity.description, style = MaterialTheme.typography.bodySmall, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(onClick = { onRemove(activity) }) {
            Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.deleteActivity))
        }
    }
}


