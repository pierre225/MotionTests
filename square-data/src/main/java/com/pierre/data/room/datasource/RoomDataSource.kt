package com.pierre.data.room.datasource

import androidx.paging.PagingSource
import com.pierre.data.repository.models.DataMotion
import com.pierre.data.repository.models.DataPosition
import com.pierre.data.room.dao.MotionDao

interface RoomDataSource {

    suspend fun insertMotion(motion: DataMotion)

    fun getPagedMotions(): PagingSource<Int, DataMotion>

    suspend fun getLastPosition(): DataPosition?

}

internal class RoomDataSourceImpl(
    private val motionDao: MotionDao
) : RoomDataSource {

    override suspend fun insertMotion(motion: DataMotion) {
        motionDao.insertMotion(motion)
        motionDao.insertPositions(motion.positions)
    }

    override fun getPagedMotions() = motionDao.getPagedMotions()

    override suspend fun getLastPosition() = motionDao.getLastPosition()

}