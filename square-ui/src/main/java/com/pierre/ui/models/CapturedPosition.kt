package com.pierre.ui.models

data class CapturedPosition(
    val squareX: Float,
    val squareY: Float,
    val touchX: Float,
    val touchY: Float,
    val timestamp: Long
)