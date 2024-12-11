package com.example.goalsgetter.features.dashboard.domain

import com.example.goalsgetter.core.utils.Result
import com.example.goalsgetter.core.utils.asResult
import com.example.goalsgetter.features.dashboard.data.DashboardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMotivationQuoteUseCase @Inject constructor(private val dashboardRepository: DashboardRepository) {
    suspend fun execute(): Flow<Result<String>> {
        return flow {
            val result = dashboardRepository.getMotivationQuote()
            emit(result)
        }.asResult()
    }
}