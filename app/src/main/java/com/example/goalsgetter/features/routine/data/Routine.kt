package com.example.goalsgetter.features.routine.data

data class Routine(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val active: Boolean = false,
    val activities: List<Activity> = emptyList()
)