package com.example.mamabear.data.repository

import com.example.mamabear.data.models.MotherModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class MotherRepository {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().getReference("mothers")

    suspend fun insertMother(mother: MotherModel) {
        try {
            database.child(mother.id).setValue(mother).await()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    suspend fun updateMother(mother: MotherModel) {
        try {
            database.child(mother.id).setValue(mother).await()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    suspend fun getMotherById(id: String): MotherModel? {
        return try {
            val snapshot = database.child(id).get().await()
            snapshot.getValue(MotherModel::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
