package com.example.goalsgetter

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import com.example.goalsgetter.core.utils.LocaleManager
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale
import javax.inject.Inject

@HiltAndroidApp
class GoalsGetter : Application() {
    @Inject
    lateinit var localeManager: LocaleManager

    override fun onCreate() {
        super.onCreate()
        val locale = localeManager.getSavedLocale()
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { base ->
            val prefs = base.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
            val langCode = prefs.getString("language_key", "en")
            val locale = Locale(langCode!!)
            Locale.setDefault(locale)
            val config = base.resources.configuration
            config.setLocale(locale)
            base.createConfigurationContext(config)
        })
    }

}
