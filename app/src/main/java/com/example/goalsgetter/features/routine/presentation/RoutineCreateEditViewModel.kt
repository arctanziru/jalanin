package com.example.goalsgetter.features.routine.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goalsgetter.core.utils.Result
import com.example.goalsgetter.features.routine.data.Activity
import com.example.goalsgetter.features.routine.data.Routine
import com.example.goalsgetter.features.routine.domain.GetRoutineByIdUseCase
import com.example.goalsgetter.features.routine.domain.SaveRoutineUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RoutineCreateEditViewModel @Inject constructor(
    private val saveRoutineUseCase: SaveRoutineUseCase,
    private val getRoutineByIdUseCase: GetRoutineByIdUseCase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _state = MutableStateFlow(RoutineFormState())
    val state: StateFlow<RoutineFormState> = _state

    fun loadRoutine(routineId: String) {
        viewModelScope.launch {
            getRoutineByIdUseCase.execute(routineId).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                    is Result.Success -> {
                        result.data?.let { routine ->
                            _state.value = RoutineFormState(
                                id = routine.id,
                                title = routine.title,
                                description = routine.description,
                                activities = routine.activities,
                                active = routine.active,
                            )
                        }
                    }
                    is Result.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            errorCode = ErrorCode.GENERIC_ERROR
                        )
                    }
                }
            }
        }
    }



    fun updateTitle(newTitle: String) {
        _state.value = _state.value.copy(title = newTitle)
    }

    fun updateDescription(newDescription: String) {
        _state.value = _state.value.copy(description = newDescription)
    }

    fun addActivity(activity: Activity) {
        val activityWithId = activity.copy(id = UUID.randomUUID().toString())
        _state.value = _state.value.copy(activities = _state.value.activities + activityWithId)
    }

    fun removeActivity(activity: Activity) {
        _state.value = _state.value.copy(activities = _state.value.activities - activity)
    }

    fun saveRoutine() {
        val currentState = _state.value
        when {
            currentState.title.isBlank() -> _state.value = currentState.copy(errorCode = ErrorCode.BLANK_TITLE)
            currentState.description.isBlank() -> _state.value = currentState.copy(errorCode = ErrorCode.BLANK_DESCRIPTION)
            currentState.activities.isEmpty() -> _state.value = currentState.copy(errorCode = ErrorCode.NO_ACTIVITIES)
            firebaseAuth.currentUser?.email.isNullOrEmpty() -> _state.value = currentState.copy(errorCode = ErrorCode.GENERIC_ERROR)
            else -> performSave(currentState)
        }
    }

    private fun performSave(currentState: RoutineFormState) {
        viewModelScope.launch {
            saveRoutineUseCase.execute(
                Routine(
                    id = currentState.id ?: "",
                    title = currentState.title,
                    description = currentState.description,
                    active = currentState.active,
                    activities = currentState.activities,
                    userEmail = firebaseAuth.currentUser!!.email!!
                )
            ).collect { result ->
                when (result) {
                    is Result.Loading -> _state.value = currentState.copy(isSaving = true, errorCode = ErrorCode.NONE)
                    is Result.Success -> _state.value = currentState.copy(isSaving = false, saveSuccess = true, errorCode = ErrorCode.NONE)
                    is Result.Error -> _state.value = currentState.copy(
                        isSaving = false,
                        saveSuccess = false,
                        errorCode = ErrorCode.GENERIC_ERROR
                    )
                }
            }
        }
    }
}

enum class ErrorCode {
    BLANK_TITLE,
    BLANK_DESCRIPTION,
    NO_ACTIVITIES,
    GENERIC_ERROR,
    NONE
}

data class RoutineFormState(
    val id: String? = null,
    val title: String = "",
    val description: String = "",
    val activities: List<Activity> = emptyList(),
    val active: Boolean = false,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val saveSuccess: Boolean? = null,
    val errorCode: ErrorCode = ErrorCode.NONE
)
