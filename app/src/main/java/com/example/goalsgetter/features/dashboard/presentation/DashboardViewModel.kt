package com.example.goalsgetter.features.dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goalsgetter.features.dashboard.domain.GetMotivationQuoteUseCase
import com.example.goalsgetter.features.goal.data.Routine
import com.example.goalsgetter.features.goal.domain.GetRoutinesUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MotivationQuote(
    val quote: String = "",
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)

data class ActiveRoutine(
    val routine: Routine? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getMotivationQuoteUseCase: GetMotivationQuoteUseCase,
    private val getRoutinesUseCase: GetRoutinesUseCase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    private val _fullName = MutableStateFlow<String?>(firebaseAuth.currentUser?.displayName)
    val fullName: StateFlow<String?> = _fullName

    private val _motivationQuote = MutableStateFlow(MotivationQuote())
    val motivationQuote: StateFlow<MotivationQuote> = _motivationQuote

    private val _activeRoutine = MutableStateFlow(ActiveRoutine())
    val activeRoutine: StateFlow<ActiveRoutine> = _activeRoutine

    init {
        fetchDashboardData()
    }

    private fun fetchDashboardData() {
        fetchMotivationQuote()
        fetchActiveRoutine()
    }

    private fun fetchMotivationQuote() {
        _motivationQuote.value = _motivationQuote.value.copy(isLoading = true)
        viewModelScope.launch {
            getMotivationQuoteUseCase.execute().collect { result ->
                when (result) {
                    is com.example.goalsgetter.core.utils.Result.Success -> {
                        _motivationQuote.value = MotivationQuote(quote = result.data)
                    }
                    is com.example.goalsgetter.core.utils.Result.Error -> {
                        _motivationQuote.value = MotivationQuote(
                            errorMessage = "Failed to fetch motivation quote: ${result.exception.message}"
                        )
                    }
                    is com.example.goalsgetter.core.utils.Result.Loading -> {
                        _motivationQuote.value = _motivationQuote.value.copy(isLoading = true)
                    }
                }
            }
        }
    }

    private fun fetchActiveRoutine() {
        _activeRoutine.value = _activeRoutine.value.copy(isLoading = true)
        viewModelScope.launch {
            val userEmail = firebaseAuth.currentUser?.email ?: ""
            getRoutinesUseCase.execute(userEmail).collect { result ->
                when (result) {
                    is com.example.goalsgetter.core.utils.Result.Success -> {
                        val routine = result.data.find { it?.active == true }
                        _activeRoutine.value = ActiveRoutine(routine)
                    }
                    is com.example.goalsgetter.core.utils.Result.Error -> {
                        _activeRoutine.value = ActiveRoutine(
                            errorMessage = "Failed to fetch active routine: ${result.exception.message}"
                        )
                    }
                    is com.example.goalsgetter.core.utils.Result.Loading -> {
                        _activeRoutine.value = _activeRoutine.value.copy(isLoading = true)
                    }
                }
            }
        }
    }

}
