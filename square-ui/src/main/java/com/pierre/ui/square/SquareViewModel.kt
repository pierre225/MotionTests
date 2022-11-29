package com.pierre.ui.square

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pierre.domain.usecases.GetLastPositionUseCase
import com.pierre.domain.usecases.InsertMotionUseCase
import com.pierre.ui.report.mapper.ReportMapper
import com.pierre.ui.report.models.ReportPosition
import com.pierre.ui.square.mapper.CaptureMapper
import com.pierre.ui.square.models.CapturedMotion
import com.pierre.ui.square.models.CapturedPosition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SquareViewModel @Inject constructor(
    private val captureMapper: CaptureMapper,
    private val reportMapper: ReportMapper,
    private val insertMotionUseCase: InsertMotionUseCase,
    private val getLastPositionUseCase: GetLastPositionUseCase,
) : ViewModel() {

    private var currentMotion: CapturedMotion? = null

    private val _lastSavedPosition =  MutableSharedFlow<ReportPosition>()
    val lastSavedPosition: SharedFlow<ReportPosition> = _lastSavedPosition

    fun getLastPosition() {
        viewModelScope.launch {
            val report = getLastPositionUseCase.invoke()
            if (report != null) {
                _lastSavedPosition.emit(reportMapper.toReportPosition(report))
            }
        }
    }

    fun startCapture() {
        currentMotion = CapturedMotion(startTime = System.currentTimeMillis())
    }

    fun stopCapture() {
        currentMotion?.endTime = System.currentTimeMillis()
        insertMotion()
    }

    fun addPosition(squareX: Float, squareY: Float, touchX: Float, touchY: Float) {
        currentMotion?.positions?.add(
            CapturedPosition(
                squareX,
                squareY,
                touchX,
                touchY,
                System.currentTimeMillis()
            )
        )
    }

    fun onExceededBounds() {
        currentMotion?.exceeded = true
    }

    fun hasExceededBounds() = currentMotion?.exceeded ?: false

    private fun insertMotion() {
        currentMotion?.also { motion ->
            currentMotion = null
            viewModelScope.launch {
                insertMotionUseCase.invoke(captureMapper.toDomain(motion))
            }
        }
    }
}