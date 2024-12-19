package com.example.goalsgetter.features.auth.domain

import com.example.goalsgetter.features.auth.data.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.goalsgetter.core.utils.Result
import com.example.goalsgetter.core.utils.asResult
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun execute(email: String, password: String): Flow<Result<FirebaseUser>> {
        return flow {
            val result = authRepository.login(email, password)
            emit(result)
        }.asResult()
    }
}
