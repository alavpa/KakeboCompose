package com.alavpa.kakebo.domain.models

data class Line(
    val id: Long,
    val amount: Long,
    val timestamp: Long,
    val type: Type,
    val category: Category,
    val isFixed: Boolean
)