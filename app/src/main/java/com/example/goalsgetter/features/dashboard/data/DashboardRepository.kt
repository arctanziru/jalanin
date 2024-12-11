package com.example.goalsgetter.features.dashboard.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DashboardRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun getMotivationQuote(): String {
        return try {
            val snapshot = firestore.collection("motivation").document("quote").get().await()
            snapshot.getString("text") ?: "Stay motivated!"
        } catch (e: Exception) {
            "Stay motivated!"
        }
    }

}