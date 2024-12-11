package com.example.goalsgetter.features.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goalsgetter.core.utils.Result
import com.example.goalsgetter.features.auth.domain.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SignUpState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val isSignUpSuccessful: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState: StateFlow<SignUpState> get() = _signUpState

    fun updateEmail(email: String) {
        _signUpState.value = _signUpState.value.copy(email = email)
    }

    fun updatePassword(password: String) {
        _signUpState.value = _signUpState.value.copy(password = password)
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _signUpState.value = _signUpState.value.copy(confirmPassword = confirmPassword)
    }

    fun signUp() {
        val currentState = _signUpState.value

        if (currentState.email.isEmpty() || currentState.password.isEmpty()) {
            _signUpState.value =
                currentState.copy(errorMessage = "Email and Password must not be empty.")
            return
        }

        if (currentState.password != currentState.confirmPassword) {
            _signUpState.value =
                currentState.copy(errorMessage = "Passwords do not match.")
            return
        }

        _signUpState.value = currentState.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            signUpUseCase.execute(currentState.email, currentState.password).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _signUpState.value = currentState.copy(isLoading = true)
                    }
                    is Result.Success -> {
                        _signUpState.value = currentState.copy(
                            isLoading = false,
                            errorMessage = null,
                            isSignUpSuccessful = true
                        )
                    }
                    is Result.Error -> {
                        _signUpState.value = currentState.copy(
                            isLoading = false,
                            errorMessage = result.exception.message,
                            isSignUpSuccessful = false
                        )
                    }
                }
            }
        }

    }
}
