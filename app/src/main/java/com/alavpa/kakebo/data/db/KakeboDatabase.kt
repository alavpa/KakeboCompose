package com.alavpa.kakebo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alavpa.kakebo.data.db.dao.LineDao
import com.alavpa.kakebo.data.db.entities.LineData

@Database(entities = [LineData::class], version = 1)
abstract class KakeboDatabase : RoomDatabase() {
    abstract fun lineDao(): LineDao
}