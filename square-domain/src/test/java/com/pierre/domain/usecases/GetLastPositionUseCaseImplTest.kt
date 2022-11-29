package com.pierre.domain.usecases

import com.pierre.data.repository.MotionRepository
import com.pierre.data.repository.models.DataPosition
import com.pierre.domain.mapper.DomainMapper
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class GetLastPositionUseCaseImplTest {

    @MockK
    private lateinit var repository: MotionRepository

    @RelaxedMockK
    private lateinit var mapper: DomainMapper

    private lateinit var useCase: GetLastPositionUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        useCase = GetLastPositionUseCaseImpl(
            motionRepository = repository,
            mapper = mapper,
        )
    }

    @After
    fun afterTests() {
        unmockkAll()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify the latest position is pulled from the repo and sent to the mapper if there is one`() = runTest {
        val dataPosition = mockk<DataPosition>()
        coEvery { repository.getLastPosition() }.returns(dataPosition)

        useCase.invoke()

        coVerifyOrder {
            repository.getLastPosition()
            mapper.toDomainPosition(dataPosition)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify the latest position is pulled from the repo but not sent if there is none`() = runTest {
        coEvery { repository.getLastPosition() }.returns(null)

        useCase.invoke()

        coVerify { repository.getLastPosition() }
        coVerify(exactly = 0) { mapper.toDomainPosition(any()) }
    }
}