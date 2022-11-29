package com.pierre.ui.report

import androidx.paging.PagingData
import com.pierre.domain.models.DomainMotion
import com.pierre.domain.usecases.GetMotionsUseCase
import com.pierre.ui.report.exceptions.ReportException
import com.pierre.ui.report.mapper.ReportMapper
import com.pierre.ui.report.models.ReportState
import com.pierre.ui.utils.ExceptionUtils
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class ReportViewModelTest {

    @RelaxedMockK
    private lateinit var getMotionsUseCase: GetMotionsUseCase

    @RelaxedMockK
    private lateinit var mapper: ReportMapper

    private lateinit var viewModel: ReportViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(UnconfinedTestDispatcher())

        viewModel = ReportViewModel(mapper, getMotionsUseCase)
    }

    @After
    fun afterTests() {
        unmockkAll()
    }

    @Test
    fun `verify the first state is a loading`() {
        assert(viewModel.state.value == ReportState.ReportLoadingState)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify get motions forwards the call to the use case`() = runTest {
        viewModel.getMotions()
        coVerify { getMotionsUseCase.invoke() }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify get motions maps the right paging data and forwards the right state when there are no errors`() = runTest {
        val flow = mockk<Flow<PagingData<DomainMotion>>>(relaxed = true)
        coEvery { getMotionsUseCase.invoke() }.returns(flow)

        assert(viewModel.state.value == ReportState.ReportLoadingState)
        viewModel.getMotions()

        flow.map {
            verify { mapper.mapPagingDataToReport(it) }
            // Verify the data in our state is the mapped data
            assert(viewModel.state.value == ReportState.ReportResultsState(mapper.mapPagingDataToReport(it)))
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify get motions forwards the right error if there is one`() = runTest {
        val exception = mockk<ReportException>()
        val expectedMessage = ExceptionUtils.messageFromException(exception)
        val expectedRetry = ExceptionUtils.canRetry(exception)

        coEvery { getMotionsUseCase.invoke() }.throws(exception)

        assert(viewModel.state.value == ReportState.ReportLoadingState)
        viewModel.getMotions()
        assert(viewModel.state.value == ReportState.ReportErrorState(expectedMessage, expectedRetry))
    }
}