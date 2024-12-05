package com.alavpa.kakebo.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.alavpa.kakebo.data.db.entities.LineData
import com.alavpa.kakebo.data.model.TypeData
import kotlinx.coroutines.flow.Flow

@Dao
interface LineDao {
    @Query("SELECT * FROM LineData ORDER BY timestamp DESC")
    fun getAll(): Flow<List<LineData>>

    @Query("SELECT * FROM LineData WHERE type = :type order by timestamp DESC")
    fun getByType(type: TypeData): Flow<List<LineData>>

    @Insert
    suspend fun insert(line: LineData)
}