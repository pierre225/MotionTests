package com.pierre.ui.models

data class CapturedMotion(
    var positions: MutableList<CapturedPosition> = mutableListOf(),
    var startTime: Long,
    var endTime: Long? = null,
    var exceeded: Boolean = false
)