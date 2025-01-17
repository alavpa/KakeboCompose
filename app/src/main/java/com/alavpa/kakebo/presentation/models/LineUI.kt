package com.alavpa.kakebo.presentation.models

data class LineUI(
    val id: Long,
    val amount: String,
    val date: String,
    val isIncome: Boolean,
    val repeatPerMonth: Boolean,
    val category: CategoryUI,
    val description: String
)
