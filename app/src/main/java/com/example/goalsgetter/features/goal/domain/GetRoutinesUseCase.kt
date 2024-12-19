package com.example.goalsgetter.features.goal.domain

import com.example.goalsgetter.core.utils.Result
import com.example.goalsgetter.core.utils.asResult
import com.example.goalsgetter.features.goal.data.Routine
import com.example.goalsgetter.features.goal.data.RoutineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRoutinesUseCase @Inject constructor(private val routineRepository: RoutineRepository) {
    suspend fun execute(userEmail: String): Flow<Result<List<Routine?>>> {
        return flow {
            val result = routineRepository.getRoutines(userEmail)
            emit(result)
        }.asResult()
    }
}