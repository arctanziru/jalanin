package com.example.goalsgetter.features.setting.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goalsgetter.core.utils.LocaleManager
import com.example.goalsgetter.features.auth.domain.LogoutUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val localeManager: LocaleManager,
    private val logoutUseCase: LogoutUseCase,
    private val firebaseAuth: FirebaseAuth // Inject FirebaseAuth
) : ViewModel() {

    // Full name of the current user
    private val _fullName = MutableStateFlow<String?>(firebaseAuth.currentUser?.displayName)
    val fullName: StateFlow<String?> = _fullName

    fun logout(onLogoutSuccess: () -> Unit) {
        viewModelScope.launch {
            logoutUseCase.execute()
            onLogoutSuccess()
        }
    }

    fun changeLanguage(langCode: String, context: Context) {
        localeManager.setLocale(context, langCode)
    }
}
