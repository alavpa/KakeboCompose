package com.alavpa.kakebo.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.alavpa.kakebo.data.db.entities.LineData

@Dao
interface LineDao {
    @Query("SELECT * FROM LineData")
    suspend fun getAll(): List<LineData>

    @Insert
    suspend fun insert(line: LineData)
}