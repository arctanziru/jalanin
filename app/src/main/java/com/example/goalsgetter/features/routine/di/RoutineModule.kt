package com.example.goalsgetter.features.routine.di

import com.example.goalsgetter.features.routine.data.RoutineRepository
import com.example.goalsgetter.features.routine.domain.DeleteRoutineUseCase
import com.example.goalsgetter.features.routine.domain.SaveRoutineUseCase
import com.example.goalsgetter.features.routine.domain.GetRoutinesUseCase
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
    fun provideDeleteActiveRoutineUseCase(repository: RoutineRepository): DeleteRoutineUseCase {
        return DeleteRoutineUseCase(repository)
    }

}
