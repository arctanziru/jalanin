package com.example.goalsgetter.features.dashboard.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.random.Random

class DashboardRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun getMotivationQuote(): String {
        return try {
            val snapshot = firestore.collection("motivation").get().await()
            val quotes = snapshot.documents.mapNotNull { it.getString("text") }
            if (quotes.isNotEmpty()) {
                quotes[Random.nextInt(quotes.size)]
            } else {
                "Stay motivated!"
            }
        } catch (e: Exception) {
            "Stay motivated!"
        }
    }
}