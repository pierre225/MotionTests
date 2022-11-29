package com.pierre.domain.usecases

import com.pierre.data.repository.MotionRepository
import com.pierre.domain.mapper.DomainMapper
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class GetPagedMotionsUseCaseImplTest {

    @MockK
    private lateinit var repository: MotionRepository

    @RelaxedMockK
    private lateinit var mapper: DomainMapper

    private lateinit var useCase: GetMotionsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        useCase = GetPagedMotionsUseCaseImpl(
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
    fun `verify the motions are pulled from the repository and then mapped in domain model`() =
        runTest {
            every { repository.getPagedMotions() }.returns(mockk(relaxed = true))

            useCase.invoke().first()

            verifyOrder {
                repository.getPagedMotions()
                mapper.mapPagingDataToDomain(any())
            }
        }

}