package com.example.goalsgetter.features.goal.di

import com.example.goalsgetter.features.goal.data.RoutineRepository
import com.example.goalsgetter.features.goal.domain.DeleteGoalUseCase
import com.example.goalsgetter.features.goal.domain.SaveRoutineUseCase
import com.example.goalsgetter.features.goal.domain.GetRoutinesUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoutineModule {
    @Provides
    @Singleton
    fun provideRoutineRepository(firestore: FirebaseFirestore): RoutineRepository {
        return RoutineRepository(firestore)
    }

    @Provides
    @Singleton
    fun provideGetRoutinesUseCase(repository: RoutineRepository): GetRoutinesUseCase {
        return GetRoutinesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveRoutineUseCase(repository: RoutineRepository): SaveRoutineUseCase {
        return SaveRoutineUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteActiveRoutineUseCase(repository: RoutineRepository): DeleteGoalUseCase {
        return DeleteGoalUseCase(repository)
    }

}
