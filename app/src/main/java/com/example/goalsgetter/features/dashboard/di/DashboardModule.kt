package com.example.goalsgetter.features.dashboard.di

import com.example.goalsgetter.features.dashboard.data.DashboardRepository
import com.example.goalsgetter.features.dashboard.domain.GetMotivationQuoteUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DashboardModule {
    @Provides
    @Singleton
    fun provideDashboardRepository(firestore: FirebaseFirestore): DashboardRepository {
        return DashboardRepository(firestore)
    }

    @Provides
    @Singleton
    fun provideGetMotivationQuoteUseCase(dashboardRepository: DashboardRepository): GetMotivationQuoteUseCase {
        return GetMotivationQuoteUseCase(dashboardRepository)
    }
}