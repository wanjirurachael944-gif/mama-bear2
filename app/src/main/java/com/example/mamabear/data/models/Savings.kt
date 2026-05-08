package com.example.mamabear.data.models

import kotlinx.serialization.Serializable


@Serializable
data class Savings(
    val id: String? = null,
    val amount : Int,
    val goal : Int,
    val month : String,
)
