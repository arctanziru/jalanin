package com.example.goalsgetter.features.routine.domain

import com.example.goalsgetter.core.utils.Result
import com.example.goalsgetter.core.utils.asResult
import com.example.goalsgetter.features.routine.data.Routine
import com.example.goalsgetter.features.routine.data.RoutineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetActiveRoutineUseCase @Inject constructor(private val routineRepository: RoutineRepository) {
    suspend fun execute(): Flow<Result<Routine?>> {
        return flow {
            val result = routineRepository.getActiveRoutine()
            emit(result)
        }.asResult()
    }
}