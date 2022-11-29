package com.pierre.data.repository

import androidx.paging.PagingSource
import com.pierre.data.repository.models.DataMotion
import com.pierre.data.room.datasource.RoomDataSource

interface MotionRepository {

    suspend fun insertMotion(motion: DataMotion)

    fun getPagedMotions(): PagingSource<Int, DataMotion>

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

    override fun getPagedMotions(): PagingSource<Int, DataMotion> = roomDataSource.getPagedMotions()

}
