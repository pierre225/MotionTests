package com.pierre.ui.report.models

data class ReportMotion(
    val date: String,
    val duration: Double,
    val exceeded: Boolean,
    val positionCount: Int,
    val positions: List<ReportPosition>
)