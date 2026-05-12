package com.example.mamabear.data.repository

import com.example.mamabear.data.models.ReminderModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ReminderRepository {
    private val database = FirebaseDatabase.getInstance().getReference("reminders")

    suspend fun insertReminder(reminder: ReminderModel) {
        val id = database.push().key ?: return
        database.child(id).setValue(reminder.copy(id = id)).await()
    }

    fun getRemindersFlow(motherId: String): Flow<List<ReminderModel>> = callbackFlow {
        val query = database.orderByChild("motherId").equalTo(motherId)
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val reminders = snapshot.children.mapNotNull { it.getValue(ReminderModel::class.java) }
                trySend(reminders)
            }
            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        query.addValueEventListener(listener)
        awaitClose { query.removeEventListener(listener) }
    }

    suspend fun updateReminder(reminder: ReminderModel) {
        if (reminder.id.isEmpty()) return
        database.child(reminder.id).setValue(reminder).await()
    }

    suspend fun deleteReminder(reminderId: String) {
        database.child(reminderId).removeValue().await()
    }

    suspend fun getRemindersByMotherId(motherId: String): List<ReminderModel> {
        return try {
            val snapshot = database.orderByChild("motherId").equalTo(motherId).get().await()
            snapshot.children.mapNotNull { it.getValue(ReminderModel::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
