package com.pierre.ui.square.mapper

import com.pierre.domain.models.DomainMotion
import com.pierre.domain.models.DomainPosition
import com.pierre.ui.square.models.CapturedMotion
import com.pierre.ui.square.models.CapturedPosition

class CaptureMapper {

    fun toDomain(capturedMotion: CapturedMotion) = DomainMotion(
        positions = capturedMotion.positions.map { toDomainPosition(it) },
        startTime = capturedMotion.startTime,
        endTime = capturedMotion.endTime ?: 0L,
        exceeded = capturedMotion.exceeded
    )

    private fun toDomainPosition(capturedPosition: CapturedPosition) =
        DomainPosition(
            squareX = capturedPosition.squareX,
            squareY = capturedPosition.squareY,
            touchX = capturedPosition.touchX,
            touchY = capturedPosition.touchY,
            timestamp = capturedPosition.timestamp
        )

}