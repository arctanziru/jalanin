package com.example.jalanin.features.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jalanin.features.auth.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoginSuccessful: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> get() = _loginState

    fun login() {
        val currentState = _loginState.value

        if (currentState.email.isEmpty() || currentState.password.isEmpty()) {
            _loginState.value = currentState.copy(errorMessage = "Email and password are required.")
            return
        }

        _loginState.value = currentState.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            val user = loginUseCase.execute(currentState.email, currentState.password)

            if (user != null) {
                _loginState.value = currentState.copy(
                    isLoading = false,
                    errorMessage = null,
                    isLoginSuccessful = true
                )
            } else {
                _loginState.value = currentState.copy(
                    isLoading = false,
                    errorMessage = "Login failed. Please check your credentials.",
                    isLoginSuccessful = false
                )
            }
        }
    }

    fun updateEmail(email: String) {
        _loginState.value = _loginState.value.copy(email = email)
    }

    fun updatePassword(password: String) {
        _loginState.value = _loginState.value.copy(password = password)
    }
}
