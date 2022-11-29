package com.pierre.ui.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.pierre.domain.usecases.GetMotionsUseCase
import com.pierre.ui.report.mapper.ReportMapper
import com.pierre.ui.report.models.ReportState
import com.pierre.ui.utils.ExceptionUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val mapper: ReportMapper,
    private val getMotionsUseCase: GetMotionsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<ReportState>(ReportState.ReportLoadingState)
    val state: StateFlow<ReportState> = _state

    /**
     * Tries to retrieved the paged songs update the state accordingly
     */
    fun getMotions() {
        viewModelScope.launch {
            // initialize with a loading state
            _state.emit(ReportState.ReportLoadingState)

            //todo maybe not here the error managing ?
            try {
                getPagingMotions()
            } catch (e: Exception) {
                onGetMotionsError(e)
            }
        }
    }

    /**
     * Use the getSongsUseCase in order to get the flow of PagingData of Domain song
     * Then map the domain song, collect the flow and emit in as a successful result
     */
    private suspend fun getPagingMotions() {
        getMotionsUseCase.invoke()
            .collectLatest { data ->
                _state.emit(ReportState.ReportResultsState(data.map { mapper.toReport(it)}))
            }
    }

    /**
     * If there is an error along the way, map this error and send it
     */
    private suspend fun onGetMotionsError(e: Exception) {
        _state.emit(
            ReportState.ReportErrorState(
                message = ExceptionUtils.messageFromException(e),
                retry = ExceptionUtils.canRetry(e)
            )
        )
    }
}