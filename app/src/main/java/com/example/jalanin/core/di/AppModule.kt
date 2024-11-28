package com.example.jalanin.di

import com.example.jalanin.core.network.FirebaseAuthService
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFirebaseAuthService(): FirebaseAuthService {
        return FirebaseAuthService(FirebaseAuth.getInstance())
    }
}
