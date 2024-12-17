package com.example.goalsgetter.features.routine.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RoutineRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun getRoutines(userEmail: String): List<Routine> {
        return try {
            val snapshot =
                firestore.collection("routines").whereEqualTo("userEmail", userEmail).get().await()
            snapshot.documents.map { document ->
                Routine(
                    id = document.id,
                    title = document.getString("title") ?: "",
                    description = document.getString("description") ?: "",
                    active = document.getBoolean("active") ?: false,
                    userEmail = document.getString("userEmail") ?: "",
                    activities = (document.get("activities") as? List<Map<String, Any>>)?.map {
                        Activity(
                            id = it["string"] as? String ?: "",
                            title = it["title"] as? String ?: "",
                            description = it["description"] as? String ?: "",
                            completed = it["completed"] as? Boolean ?: false
                        )
                    } ?: emptyList()
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun saveRoutine(routine: Routine): Boolean {
        return try {
            firestore.collection("routines")
                .document(routine.id.takeIf { it.isNotEmpty() } ?: firestore.collection("routines")
                    .document().id)
                .set(routine)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getRoutineById(routineId: String): Routine? {
        return try {
            val document = firestore.collection("routines").document(routineId).get().await()
            document?.let {
                Routine(
                    id = it.id,
                    title = it.getString("title") ?: "",
                    description = it.getString("description") ?: "",
                    active = it.getBoolean("active") ?: false,
                    userEmail = it.getString("userEmail") ?: "",
                    activities = (it.get("activities") as? List<Map<String, Any>>)?.map {
                        Activity(
                            id = it["id"] as? String ?: "",
                            title = it["title"] as? String ?: "",
                            description = it["description"] as? String ?: "",
                            completed = it["completed"] as? Boolean ?: false
                        )
                    } ?: emptyList()
                )
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun deleteRoutine(routineId: String): Boolean {
        return try {
            firestore.collection("routines").document(routineId).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }
}