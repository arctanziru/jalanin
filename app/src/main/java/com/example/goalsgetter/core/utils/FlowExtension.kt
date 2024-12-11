package com.example.goalsgetter.core.utils

import kotlinx.coroutines.flow.*

fun <T> Flow<T>.asResult(): Flow<Result<T>> {
    return this
        .map<T, Result<T>> { Result.Success(it) } // Wrap emitted values in Success
        .onStart { emit(Result.Loading) } // Emit Loading at the start
        .catch { emit(Result.Error(it)) } // Wrap exceptions in Error
}
