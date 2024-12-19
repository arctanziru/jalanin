package com.example.goalsgetter.features.goal.data

data class Routine(
    val userEmail: String,
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val active: Boolean = false,
    val activities: List<Activity> = emptyList()
)