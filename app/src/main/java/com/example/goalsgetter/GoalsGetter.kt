package com.example.goalsgetter

import android.app.Application
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
}
