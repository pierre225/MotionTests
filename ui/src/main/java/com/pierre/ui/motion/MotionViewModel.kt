package com.pierre.ui.motion

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pierre.domain.usecases.GetMotionsUseCase
import com.pierre.domain.usecases.InsertMotionUseCase
import com.pierre.ui.mapper.UiMapper
import com.pierre.ui.models.CapturedMotion
import com.pierre.ui.models.CapturedPosition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MotionViewModel @Inject constructor(
    private val mapper: UiMapper,
    private val insertMotionUseCase: InsertMotionUseCase,
    private val getMotionsUseCase: GetMotionsUseCase,
) : ViewModel() {

    private var currentMotion : CapturedMotion? = null

    fun startCapture() {
        currentMotion = CapturedMotion(startTime = System.currentTimeMillis())
    }

    fun addPosition(squareX: Float, squareY: Float, touchX: Float, touchY: Float) {
        currentMotion?.positions?.add(CapturedPosition(squareX, squareY, touchX, touchY, System.currentTimeMillis()))
    }

    fun exceededBounds() {
        currentMotion?.exceeded = true
    }

    fun stopCapture() {
        currentMotion?.endTime = System.currentTimeMillis()
        insertMotion()
    }

    private fun insertMotion() {
        currentMotion?.also { motion ->
            if (motion != null) {
                viewModelScope.launch {
                    insertMotionUseCase.invoke(mapper.toDomain(motion))
                }
            }
        }
    }

    fun show(context: Context) {
        viewModelScope.launch {
            Log.d("testtest", getMotionsUseCase.invoke().toString())
        }
    }
}