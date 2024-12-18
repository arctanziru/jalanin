package com.example.goalsgetter.features.auth.domain

import com.example.goalsgetter.features.auth.data.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.goalsgetter.core.utils.Result
import com.example.goalsgetter.core.utils.asResult
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun execute(email: String, password: String, fullName: String): Flow<Result<FirebaseUser>> {
        return flow {
            val result = authRepository.register(email, password, fullName)
            emit(result)
        }.asResult()
    }
}
