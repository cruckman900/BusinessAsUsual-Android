package com.example.domain.model

data class Module(
    val id: String,
    val name: String,
    val description: String,
    val iconRes: Int // Android drawable resource ID
)