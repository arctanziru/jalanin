package com.example.jalanin.features.auth.data

import com.example.jalanin.core.network.FirebaseAuthService
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuthService: FirebaseAuthService
) {

    suspend fun login(email: String, password: String): FirebaseUser? {
        return firebaseAuthService.login(email, password)
    }

    suspend fun register(email: String, password: String): FirebaseUser? {
        return firebaseAuthService.register(email, password)
    }

    fun logout() {
        firebaseAuthService.logout()
    }
}
