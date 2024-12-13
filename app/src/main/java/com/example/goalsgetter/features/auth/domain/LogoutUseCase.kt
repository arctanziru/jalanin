package com.example.goalsgetter.features.auth.domain

import com.example.goalsgetter.features.auth.data.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.goalsgetter.core.utils.Result
import com.example.goalsgetter.core.utils.asResult
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun execute(): Flow<Result<Unit?>> {
        return flow {
            val result = authRepository.logout()
            emit(result)
        }.asResult()
    }
}
