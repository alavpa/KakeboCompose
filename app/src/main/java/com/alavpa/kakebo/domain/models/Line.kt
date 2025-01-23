package com.alavpa.kakebo.domain.models

data class Line(
    val id: Long = 0,
    val amount: Int,
    val description: String,
    val timestamp: Long,
    val type: Type,
    val category: Category,
    val repeat: Boolean
)
