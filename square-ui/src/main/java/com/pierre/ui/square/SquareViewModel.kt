package com.pierre.ui.square

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pierre.domain.usecases.InsertMotionUseCase
import com.pierre.ui.square.mapper.CaptureMapper
import com.pierre.ui.square.models.CapturedMotion
import com.pierre.ui.square.models.CapturedPosition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SquareViewModel @Inject constructor(
    private val mapper: CaptureMapper,
    private val insertMotionUseCase: InsertMotionUseCase,
) : ViewModel() {

    private var currentMotion: CapturedMotion? = null

    fun startCapture() {
        currentMotion = CapturedMotion(startTime = System.currentTimeMillis())
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

    fun stopCapture() {
        currentMotion?.endTime = System.currentTimeMillis()
        insertMotion()
    }

    private fun insertMotion() {
        currentMotion?.also { motion ->
            currentMotion = null
            viewModelScope.launch {
                insertMotionUseCase.invoke(mapper.toDomain(motion))
            }
        }
    }
}