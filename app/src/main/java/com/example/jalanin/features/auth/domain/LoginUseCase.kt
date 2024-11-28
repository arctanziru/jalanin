package com.example.jalanin.features.auth.domain

import com.example.jalanin.features.auth.data.AuthRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun execute(email: String, password: String): FirebaseUser? {
        return authRepository.login(email, password)
    }
}
