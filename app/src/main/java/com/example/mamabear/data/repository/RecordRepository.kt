package com.example.mamabear.data.repository

import com.example.mamabear.data.models.HealthRecordsModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class RecordRepository {
    private val database = FirebaseDatabase.getInstance().getReference("health_records")

    suspend fun insertRecord(record: HealthRecordsModel) {
        val id = database.push().key ?: return
        database.child(id).setValue(record.copy(id = id)).await()
    }

    fun getRecordsFlow(motherId: String): Flow<List<HealthRecordsModel>> = callbackFlow {
        val query = database.orderByChild("motherId").equalTo(motherId)
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val records = snapshot.children.mapNotNull { it.getValue(HealthRecordsModel::class.java) }
                trySend(records)
            }
            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        query.addValueEventListener(listener)
        awaitClose { query.removeEventListener(listener) }
    }

    suspend fun updateRecord(record: HealthRecordsModel) {
        if (record.id.isEmpty()) return
        database.child(record.id).setValue(record).await()
    }

    suspend fun deleteRecord(recordId: String) {
        database.child(recordId).removeValue().await()
    }
}
