package com.pierre.data.repository

import com.pierre.data.repository.models.DataMotion
import com.pierre.data.room.datasource.RoomDataSource

interface MotionRepository {

    suspend fun insertMotion(motion: DataMotion)

    suspend fun getMotions(): List<DataMotion>

}

/**
 * Our repo only works with a room data source, but we could extend that with other data source like remotes...
 */
internal class MotionRepositoryImpl(
    private val roomDataSource: RoomDataSource
) : MotionRepository {

    override suspend fun insertMotion(motion: DataMotion) {
        roomDataSource.insertMotion(motion)
    }

    override suspend fun getMotions(): List<DataMotion> = roomDataSource.getMotions()

}
