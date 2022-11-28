package com.pierre.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pierre.data.repository.models.DataMotion

@Dao
interface MotionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMotion(motion: DataMotion)

    @Query("SELECT * FROM motions")
    suspend fun getMotions(): List<DataMotion>

}