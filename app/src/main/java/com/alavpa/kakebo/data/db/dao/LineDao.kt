package com.alavpa.kakebo.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.alavpa.kakebo.data.db.entities.LineData
import kotlinx.coroutines.flow.Flow

@Dao
interface LineDao {
    @Query(
        "SELECT * FROM LineData WHERE (month = :month AND year = :year) OR isFixed = 1 " +
            "ORDER BY timestamp DESC"
    )
    fun getAllFromCurrentMonthYear(
        month: Int,
        year: Int
    ): Flow<List<LineData>>

    @Insert
    suspend fun insert(lineData: LineData)

    @Query("DELETE FROM LineData WHERE id = :id")
    suspend fun remove(id: Long)
}
