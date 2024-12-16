package com.example.goalsgetter.features.routine.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goalsgetter.core.utils.Result
import com.example.goalsgetter.features.routine.data.Routine
import com.example.goalsgetter.features.routine.domain.DeleteRoutineUseCase
import com.example.goalsgetter.features.routine.domain.GetRoutinesUseCase
import com.example.goalsgetter.features.routine.domain.SaveRoutineUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineViewModel @Inject constructor(
    private val getRoutinesUseCase: GetRoutinesUseCase,
    private val saveRoutineUseCase: SaveRoutineUseCase,
    private val deleteRoutineUseCase: DeleteRoutineUseCase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    private val _routinesState = MutableStateFlow(RoutinesState())
    val routinesState: StateFlow<RoutinesState> = _routinesState

    private val _dialogState = MutableStateFlow<Routine?>(null)
    val dialogState: StateFlow<Routine?> = _dialogState


    fun showDialog(routine: Routine) {
        _dialogState.value = routine
    }

    fun hideDialog() {
        _dialogState.value = null
    }

    init {
        fetchRoutines()
    }

    private fun fetchRoutines() {
        val userEmail = firebaseAuth.currentUser?.email ?: ""
        viewModelScope.launch {
            getRoutinesUseCase.execute(userEmail).collect { result ->
                when (result) {
                    is Result.Loading -> _routinesState.value =
                        _routinesState.value.copy(isLoading = true)

                    is Result.Success -> _routinesState.value =
                        RoutinesState(routines = result.data)

                    is Result.Error -> _routinesState.value =
                        RoutinesState(errorMessage = result.exception.message)
                }
            }
        }
    }

    fun toggleActiveRoutine(routineId: String, active: Boolean) {
        viewModelScope.launch {
            val routine = _routinesState.value.routines.find { it?.id == routineId }
            if (routine != null) {
                val updatedRoutine = routine.copy(active = active)
                saveRoutine(updatedRoutine)
            }
        }
    }

    fun toggleActivityCompleted(routineId: String, activityId: String, completed: Boolean) {
        viewModelScope.launch {
            val routine = _routinesState.value.routines.find { it?.id == routineId }
            if (routine != null) {
                val updatedActivities = routine.activities.map { activity ->
                    if (activity.id == activityId) {
                        activity.copy(completed = completed)
                    } else {
                        activity
                    }
                }

                val updatedRoutine = routine.copy(activities = updatedActivities)

                saveRoutine(updatedRoutine)
            }
        }
    }

    private suspend fun saveRoutine(routine: Routine) {
        val userEmail = firebaseAuth.currentUser?.email ?: ""

        saveRoutineUseCase.execute(
            Routine(
                id = routine.id,
                title = routine.title,
                description = routine.description,
                activities = routine.activities,
                active = routine.active,
                userEmail = userEmail
            )
        ).collect { result ->
            when (result) {
                is Result.Success -> {
                    fetchRoutines()
                }

                is Result.Loading -> {}
                is Result.Error -> _routinesState.value = _routinesState.value.copy(
                    isLoading = false,
                    errorMessage = result.exception.message
                )
            }
        }
    }

    fun deleteRoutine(routineId: String) {
        viewModelScope.launch {
            deleteRoutineUseCase.execute(routineId).collect { result ->
                when (result) {
                    is Result.Error -> _routinesState.value = _routinesState.value.copy(
                        isLoading = false,
                        errorMessage = result.exception.message
                    )
                    is Result.Loading -> {}
                    is Result.Success -> {
                        fetchRoutines()
                    }
                }
            }
        }
    }

}

data class RoutinesState(
    val routines: List<Routine?> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

