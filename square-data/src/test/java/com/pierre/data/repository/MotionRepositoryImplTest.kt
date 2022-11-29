package com.pierre.data.repository

import com.pierre.data.repository.models.DataMotion
import com.pierre.data.room.datasource.RoomDataSource
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Tests of the repository, in this example, the tests are only checking the calls are forwarded to the unique data source.
 * If we had several data sources, we could check the logic that choose what data source to use
 */
internal class MotionRepositoryImplTest {

    @RelaxedMockK
    private lateinit var roomDataSource: RoomDataSource

    private lateinit var repository: MotionRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        repository = MotionRepositoryImpl(
            roomDataSource = roomDataSource,
        )
    }

    @After
    fun afterTests() {
        unmockkAll()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Insert motion is forwarded with the right params`() = runTest {
        val dataMotion = mockk<DataMotion>()
        repository.insertMotion(dataMotion)
        coVerify { roomDataSource.insertMotion(dataMotion) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get paged motions is forwarded to the room data source`() = runTest {
        repository.getPagedMotions()
        coVerify { roomDataSource.getPagedMotions() }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get last position is forwarded to the room data source`() = runTest {
        repository.getLastPosition()
        coVerify { roomDataSource.getLastPosition() }
    }
}