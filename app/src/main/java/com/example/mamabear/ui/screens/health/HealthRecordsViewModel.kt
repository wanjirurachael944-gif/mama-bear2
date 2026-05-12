package com.example.mamabear.ui.screens.health

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mamabear.data.models.HealthRecordsModel
import com.example.mamabear.data.repository.RecordRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HealthRecordsViewModel : ViewModel() {

    private val repository = RecordRepository()
    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    private val _records = MutableStateFlow<List<HealthRecordsModel>>(emptyList())
    val records: StateFlow<List<HealthRecordsModel>> = _records

    private val motherRepository = com.example.mamabear.data.repository.MotherRepository()
    private val _mother = MutableStateFlow<com.example.mamabear.data.models.MotherModel?>(null)
    val mother: StateFlow<com.example.mamabear.data.models.MotherModel?> = _mother

    private val reminderRepository = com.example.mamabear.data.repository.ReminderRepository()
    private val _reminders = MutableStateFlow<List<com.example.mamabear.data.models.ReminderModel>>(emptyList())
    val reminders: StateFlow<List<com.example.mamabear.data.models.ReminderModel>> = _reminders

    init {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            viewModelScope.launch {
                repository.getRecordsFlow(user.uid).collectLatest { updatedRecords ->
                    _records.value = updatedRecords
                }
            }
            viewModelScope.launch {
                reminderRepository.getRemindersFlow(user.uid).collectLatest { updatedReminders ->
                    _reminders.value = updatedReminders.filter { 
                        it.title.contains("Appointment", ignoreCase = true) || 
                        it.title.contains("Clinic", ignoreCase = true) ||
                        it.title.contains("Visit", ignoreCase = true)
                    }.sortedBy { it.date }
                }
            }
            viewModelScope.launch {
                val m = motherRepository.getMotherById(user.uid)
                _mother.value = m
            }
        }
    }

    fun fetchRecords() {
        // No longer needed as we are using Flow/ValueEventListener
    }

    fun addRecord(healthRecord: HealthRecordsModel) {
        viewModelScope.launch {
            try {
                repository.insertRecord(healthRecord)
                _message.value = "Record Added"
            } catch (e: Exception) {
                _message.value = e.message.toString()
            }
        }
    }

    fun updateRecord(healthRecord: HealthRecordsModel) {
        viewModelScope.launch {
            try {
                repository.updateRecord(healthRecord)
                _message.value = "Record Updated"
            } catch (e: Exception) {
                _message.value = e.message.toString()
            }
        }
    }

    fun deleteRecord(recordId: String) {
        viewModelScope.launch {
            try {
                repository.deleteRecord(recordId)
                _message.value = "Record Deleted"
            } catch (e: Exception) {
                _message.value = e.message.toString()
            }
        }
    }

    fun getTrimester(): Int {
        val weeks = mother.value?.weeksPregnant?.toIntOrNull() ?: 0
        return when {
            weeks <= 13 -> 1
            weeks <= 26 -> 2
            else -> 3
        }
    }

    fun getProgressSummary(): String {
        val sortedRecords = _records.value.sortedBy { it.date }
        if (sortedRecords.size < 2) return "Record more visits to see progress."
        
        val first = sortedRecords.first()
        val last = sortedRecords.last()
        
        val weightDiff = try {
            val fW = first.weight.filter { it.isDigit() || it == '.' }.toDoubleOrNull() ?: 0.0
            val lW = last.weight.filter { it.isDigit() || it == '.' }.toDoubleOrNull() ?: 0.0
            lW - fW
        } catch (e: Exception) { 0.0 }

        return if (weightDiff >= 0) "You've gained ${String.format("%.1f", weightDiff)} kg since your first visit."
        else "You've lost ${String.format("%.1f", -weightDiff)} kg since your first visit."
    }
}
