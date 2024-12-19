package com.example.goalsgetter.features.goal.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.goalsgetter.features.goal.data.Routine
import com.example.goalsgetter.ui.components.CustomButton

@Composable
fun RoutineActionDialog(
    routine: Routine,
    active: Boolean,
    onEditClick: () -> Unit,
    onActiveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Routine Actions") },
        text = { Text("Choose an action for '${routine.title}'") },
        confirmButton = {
            Column {
                CustomButton(
                    onClick = onEditClick,
                    text = "Edit Routine",
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                CustomButton(
                    onClick = onActiveClick,
                    modifier = Modifier.fillMaxWidth(),
                    text = if (!active) "Select Routine" else "Unselect Routine",
                )
                Spacer(modifier = Modifier.height(8.dp))
                CustomButton(
                    color = Color.Red,
                    onClick = onDeleteClick,
                    modifier = Modifier.fillMaxWidth(),
                    text = "Delete Routine",
                )
            }
        },
    )
}
