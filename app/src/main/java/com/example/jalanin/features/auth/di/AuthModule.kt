package com.example.jalanin.features.auth.di

import com.example.jalanin.features.auth.data.AuthRepository
import com.example.jalanin.features.auth.domain.LoginUseCase
import com.example.jalanin.features.auth.presentation.LoginViewModel
import com.example.jalanin.core.network.FirebaseAuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    fun provideAuthRepository(firebaseAuthService: FirebaseAuthService): AuthRepository {
        return AuthRepository(firebaseAuthService)
    }

    @Provides
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Provides
    fun provideLoginViewModel(loginUseCase: LoginUseCase): LoginViewModel {
        return LoginViewModel(loginUseCase)
    }
}
