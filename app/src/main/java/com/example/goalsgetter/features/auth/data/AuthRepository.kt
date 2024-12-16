package com.example.goalsgetter.features.auth.data

import com.example.goalsgetter.core.network.FirebaseAuthService
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuthService: FirebaseAuthService
) {

    suspend fun login(email: String, password: String): FirebaseUser? {
        return firebaseAuthService.login(email, password)
    }

    suspend fun register(email: String, password: String, fullName: String): FirebaseUser? {
        val user = firebaseAuthService.register(email, password)
        user?.let {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(fullName)
                .build()
            it.updateProfile(profileUpdates).await()
        }
        return user
    }

    fun logout() {
        firebaseAuthService.logout()
    }
}
                                                                                            