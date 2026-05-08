package com.example.mamabear.data.models

import kotlinx.serialization.Serializable

@Serializable
data class HomeModel(
    val id : Int = 0,
    val name : String,
    val weeksPregnant: Int,
    val trimester : String,
    val savings : Int = 0,
    val savingsGoal : Int = 0
)
