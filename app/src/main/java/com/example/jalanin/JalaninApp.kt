package com.example.jalanin

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JalaninApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
