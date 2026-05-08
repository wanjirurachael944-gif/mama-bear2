package com.example.mamabear.ui.screens.Records



import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class HealthRecordsViewModel : ViewModel() {

    // 🔹 HEALTH RECORDS LIST
    private val _records =
        mutableStateOf(
            listOf(
                "Blood Pressure Check",
                "Ultrasound Appointment",
                "Routine ANC Visit",
                "Weight Monitoring"
            )
        )

    val records: State<List<String>> = _records

    // 🔥 ADD NEW RECORD
    fun addRecord(record: String) {

        _records.value =
            _records.value + record
    }

    // 🔥 DELETE RECORD
    fun deleteRecord(record: String) {

        _records.value =
            _records.value - record
    }
}