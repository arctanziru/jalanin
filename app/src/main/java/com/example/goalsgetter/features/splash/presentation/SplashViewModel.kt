package com.example.goalsgetter.features.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SplashNavigation {
    data object ToDashboard : SplashNavigation()
    data object ToLogin : SplashNavigation()
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _navigation = MutableStateFlow<SplashNavigation?>(null)
    val navigation: StateFlow<SplashNavigation?> = _navigation

    init {
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        viewModelScope.launch {
            delay(1000L) // Optional: Animation delay
            val isUserLoggedIn = firebaseAuth.currentUser != null
            _navigation.value = if (isUserLoggedIn) {
                SplashNavigation.ToDashboard
            } else {
                SplashNavigation.ToLogin
            }
        }
    }
}
