package com.pierre.ui.square

import com.pierre.domain.models.DomainPosition
import com.pierre.domain.usecases.GetLastPositionUseCase
import com.pierre.domain.usecases.InsertMotionUseCase
import com.pierre.ui.report.mapper.ReportMapper
import com.pierre.ui.square.mapper.CaptureMapper
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class SquareViewModelTest {

    @RelaxedMockK
    private lateinit var insertMotionsUseCase: InsertMotionUseCase

    @RelaxedMockK
    private lateinit var getLastPositionUseCase: GetLastPositionUseCase

    @RelaxedMockK
    private lateinit var captureMapper: CaptureMapper

    @RelaxedMockK
    private lateinit var reportMapper: ReportMapper

    private lateinit var viewModel: SquareViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(UnconfinedTestDispatcher())

        viewModel = SquareViewModel(captureMapper, reportMapper, insertMotionsUseCase, getLastPositionUseCase)
    }

    @After
    fun afterTests() {
        unmockkAll()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify stopping the capture does not send the motion if the capture didn't start`() = runTest {
        viewModel.stopCapture()
        coVerify(exactly = 0) {
            captureMapper.toDomain(any())
            insertMotionsUseCase.invoke(any())
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify stopping the capture maps and sends the motion if the capture started`() = runTest {
        viewModel.startCapture()
        viewModel.stopCapture()
        coVerifyOrder {
            captureMapper.toDomain(any())
            insertMotionsUseCase.invoke(any())
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify get last position is forwarded to the use case`() = runTest {
        viewModel.getLastPosition()

        coVerify {
            getLastPositionUseCase.invoke()
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify get last position is not sent if null`() = runTest {
        coEvery { getLastPositionUseCase.invoke() }.returns(null)
        viewModel.getLastPosition()

        coVerify { getLastPositionUseCase.invoke() }
        coVerify(exactly = 0) { reportMapper.toReportPosition(any()) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify get last position is sent if not null`() = runTest {
        val domainPosition = mockk<DomainPosition>()
        coEvery { getLastPositionUseCase.invoke() }.returns(domainPosition)
        viewModel.getLastPosition()

        coVerify { getLastPositionUseCase.invoke() }
        coVerify(exactly = 1) { reportMapper.toReportPosition(domainPosition) }
    }

}