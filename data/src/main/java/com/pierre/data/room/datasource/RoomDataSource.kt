package com.pierre.data.room.datasource

import com.pierre.data.repository.models.DataMotion
import com.pierre.data.room.dao.MotionDao

interface RoomDataSource {

    suspend fun insertMotion(motion: DataMotion)

    suspend fun getMotions(): List<DataMotion>
}

internal class RoomDataSourceImpl(
    private val motionDao: MotionDao
) : RoomDataSource {

    override suspend fun insertMotion(motion: DataMotion) {
        motionDao.insertMotion(motion)
    }

    override suspend fun getMotions(): List<DataMotion> = motionDao.getMotions()

}