package com.alavpa.kakebo.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alavpa.kakebo.data.model.CategoryData
import com.alavpa.kakebo.data.model.TypeData

@Entity
data class LineData(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Long,
    val description: String,
    val timestamp: Long,
    val month: Int,
    val year: Int,
    val type: TypeData,
    val category: CategoryData,
    val isFixed: Boolean
)
