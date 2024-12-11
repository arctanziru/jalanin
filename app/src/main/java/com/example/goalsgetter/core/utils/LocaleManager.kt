package com.example.goalsgetter.core.utils

import android.content.Context
import android.content.SharedPreferences
import java.util.Locale
import javax.inject.Inject

class LocaleManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        private const val LANGUAGE_KEY = "language_key"
    }

    fun setLocale(context: Context, langCode: String) {
        val config = context.resources.configuration
        val locale = Locale(langCode)
        Locale.setDefault(locale)
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)

        // Save preference
        sharedPreferences.edit().putString(LANGUAGE_KEY, langCode).apply()
    }

    fun getSavedLocale(): Locale {
        val langCode = sharedPreferences.getString(LANGUAGE_KEY, "en") ?: "en"
        return Locale(langCode)
    }
}
