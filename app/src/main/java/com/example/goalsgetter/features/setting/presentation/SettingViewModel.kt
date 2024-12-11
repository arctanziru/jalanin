package com.example.goalsgetter.features.setting.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.goalsgetter.core.utils.LocaleManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val localeManager: LocaleManager
) : ViewModel() {

    fun changeLanguage(langCode: String, context: Context) {
        localeManager.setLocale(context, langCode)
    }
}
