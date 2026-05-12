package com.example.mamabear.data.models

import kotlinx.serialization.Serializable

@Serializable
data class HealthRecordsModel (
    val id: String = "",
    val motherId: String = "",
    val bloodPressure: String = "",
    val weight: String = "",
    val labResults: String = "",
    val vaccine : String = "",
    val date: String = "" // Added date for the visit
)
