package com.pierre.domain.usecases

import com.pierre.data.repository.MotionRepository
import com.pierre.data.repository.models.DataMotion
import com.pierre.domain.mapper.DomainMapper
import com.pierre.domain.models.DomainMotion
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class InsertMotionUseCaseImplTest {

    @MockK
    private lateinit var repository: MotionRepository

    @RelaxedMockK
    private lateinit var mapper: DomainMapper

    private lateinit var useCase: InsertMotionUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        useCase = InsertMotionUseCaseImpl(
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
    fun `verify the it maps and sends to the repo the right motion`() = runTest {
        val domainMotion = mockk<DomainMotion>()
        val dataMotion = mockk<DataMotion>()
        every { mapper.toData(domainMotion) }.returns(dataMotion)
        coEvery { repository.insertMotion(dataMotion) } just Runs

        useCase.invoke(domainMotion)

        coVerifyOrder {
            mapper.toData(domainMotion)
            repository.insertMotion(dataMotion)
        }
    }
}