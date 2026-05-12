package com.example.mamabear.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ReminderModel (
    val id: String = "",
    val motherId: String = "",
    val title :String = "",
    val description : String = "",
    val date: String = ""
)
