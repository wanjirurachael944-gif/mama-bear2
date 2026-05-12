package com.example.mamabear.data.models

import kotlinx.serialization.Serializable

@Serializable
data class SavingsModel(
    val id: String = "",

    val motherId: String = "",

    val amountSaved: Int = 0,

    val savingsGoal: Int = 0

)