package com.example.goalsgetter.features.goal.domain

import com.example.goalsgetter.core.utils.Result
import com.example.goalsgetter.core.utils.asResult
import com.example.goalsgetter.features.goal.data.Routine
import com.example.goalsgetter.features.goal.data.RoutineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRoutineByIdUseCase @Inject constructor(
    private val routineRepository: RoutineRepository
) {
    suspend fun execute(routineId: String): Flow<Result<Routine?>> {
        return flow {
            val result = routineRepository.getRoutineById(routineId)
            emit(result)
        }.asResult()
    }
}
