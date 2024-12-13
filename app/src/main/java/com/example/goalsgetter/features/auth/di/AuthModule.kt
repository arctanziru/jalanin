package com.example.goalsgetter.features.auth.di

import com.example.goalsgetter.features.auth.data.AuthRepository
import com.example.goalsgetter.features.auth.domain.LoginUseCase
import com.example.goalsgetter.core.network.FirebaseAuthService
import com.example.goalsgetter.features.auth.domain.LogoutUseCase
import com.example.goalsgetter.features.auth.domain.SignUpUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuthService: FirebaseAuthService): AuthRepository {
        return AuthRepository(firebaseAuthService)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideSignupUseCase(authRepository: AuthRepository): SignUpUseCase {
        return SignUpUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideLogoutUseCase(authRepository: AuthRepository): LogoutUseCase {
        return LogoutUseCase(authRepository)
    }
}
