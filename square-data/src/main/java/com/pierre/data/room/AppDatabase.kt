package com.pierre.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pierre.data.repository.models.DataMotion
import com.pierre.data.repository.models.DataPosition
import com.pierre.data.room.converters.Converters
import com.pierre.data.room.dao.MotionDao

@Database(entities = [DataMotion::class, DataPosition::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun motionDao(): MotionDao
}
