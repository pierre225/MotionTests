package com.pierre.data.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pierre.data.repository.models.DataMotion
import com.pierre.data.repository.models.DataPosition

@Dao
interface MotionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMotion(motion: DataMotion)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPositions(positions: List<DataPosition>)

    @Query("SELECT * FROM motions")
    fun getPagedMotions(): PagingSource<Int, DataMotion>

    @Query("SELECT * FROM positions ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastPosition(): DataPosition?
}