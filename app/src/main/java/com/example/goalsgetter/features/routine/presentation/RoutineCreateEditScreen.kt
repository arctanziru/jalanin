package com.example.goalsgetter.features.routine.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.TextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
                title = { Text(if (isEdit) "Edit Rencana" else "Tambah Rencana") },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = state.title,
                onValueChange = viewModel::updateTitle,
                label = { Text("Nama Rencana") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = state.description,
                onValueChange = viewModel::updateDescription,
                label = { Text("Deskripsi Rencana") },
                modifier = Modifier.fillMaxWidth()
            )

            Text("Daftar Aktivitas", style = MaterialTheme.typography.titleMedium)

            LazyColumn {
                items(state.activities) { activity ->
                    ActivityItem(
                        activity = activity,
                        onRemove = { viewModel.removeActivity(it) }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = newActivityTitle,
                    onValueChange = { newActivityTitle = it },
                    label = { Text("Nama Aktivitas") },
                    modifier = Modifier.weight(1f)
                )
                TextField(
                    value = newActivityDescription,
                    onValueChange = { newActivityDescription = it },
                    label = { Text("Deskripsi Aktivitas") },
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = {
                        if (newActivityTitle.isNotBlank() && newActivityDescription.isNotBlank()) {
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
                    Text("+")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Error Message
            state.errorMessage?.let { errorMessage ->
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            CustomButton(
                color = Primary,
                onClick = { viewModel.saveRoutine() },
                text = if (state.isSaving) "Menyimpan..." else if (isEdit) "Update Rencana" else "Simpan Rencana",
                textColor = MaterialTheme.colorScheme.onPrimary,
                enabled = !state.isSaving,
                buttonType = ButtonType.CONTAINED,
                modifier = Modifier.fillMaxWidth()
            )
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
        Column {
            Text(activity.title, style = MaterialTheme.typography.bodyMedium)
            Text(activity.description, style = MaterialTheme.typography.bodySmall)
        }
        IconButton(onClick = { onRemove(activity) }) {
            Icon(Icons.Default.Delete, contentDescription = "Delete Activity")
        }
    }
}


