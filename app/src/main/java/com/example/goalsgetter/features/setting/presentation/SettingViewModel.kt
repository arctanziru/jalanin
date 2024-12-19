package com.example.goalsgetter.features.setting.presentation

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goalsgetter.core.utils.LocaleManager
import com.example.goalsgetter.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val localeManager: LocaleManager,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _fullName = MutableStateFlow(firebaseAuth.currentUser?.displayName)
    val fullName: StateFlow<String?> = _fullName

    private val _language = MutableStateFlow(Locale.getDefault().language)
    val language: StateFlow<String> = _language

    fun changeLanguage(langCode: String, context: Context) {
        localeManager.setLocale(context, langCode)
        _language.value = langCode
        restartActivity(context)
    }

    fun logout(onLogoutSuccess: () -> Unit) {
        viewModelScope.launch {
            firebaseAuth.signOut()
            onLogoutSuccess()
        }
    }

    private fun restartActivity(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
    }

}
