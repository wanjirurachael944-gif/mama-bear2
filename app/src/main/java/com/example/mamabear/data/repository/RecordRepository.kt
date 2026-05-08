package com.example.mamabear.data.repository

class HealthRecordsRepository {

    fun getRecords(): List<String> {

        return listOf(

            "Blood Pressure Check",
            "Ultrasound Appointment",
            "Routine ANC Visit",
            "Weight Monitoring"
        )
    }

    fun addRecord(
        currentRecords: List<String>,
        newRecord: String
    ): List<String> {

        return currentRecords + newRecord
    }

    fun deleteRecord(
        currentRecords: List<String>,
        record: String
    ): List<String> {

        return currentRecords - record
    }
}
