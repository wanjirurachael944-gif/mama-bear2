package com.example.mamabear.data.models

import kotlinx.serialization.Serializable

@Serializable
data class MotherModel(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val weeksPregnant: String = "",
    val dueDate: String = "",
    val spouseName: String = "",
    val spouseContact: String = "",
    val profilePictureUrl: String? = null
)
