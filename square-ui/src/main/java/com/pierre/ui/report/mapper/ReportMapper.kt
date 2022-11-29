package com.pierre.ui.report.mapper

import com.pierre.domain.models.DomainMotion
import com.pierre.domain.models.DomainPosition
import com.pierre.ui.report.models.ReportMotion
import com.pierre.ui.report.models.ReportPosition
import com.pierre.ui.square.models.CapturedMotion
import com.pierre.ui.square.models.CapturedPosition
import com.pierre.ui.utils.DateUtils
import java.time.format.DateTimeFormatter

class ReportMapper {

    fun toReport(domainMotion: DomainMotion) = ReportMotion(
        date = DateUtils.convertLongToTime(domainMotion.startTime),
        duration = duration(domainMotion),
        exceeded = domainMotion.exceeded,
        positionCount = domainMotion.positions.size,
        positions = domainMotion.positions.map { toReportPosition(it) }
    )

    private fun toReportPosition(domainPosition: DomainPosition) = ReportPosition(
        squareX = domainPosition.squareX,
        squareY = domainPosition.squareY,
        touchX = domainPosition.touchX,
        touchY = domainPosition.touchY,
        timestamp = domainPosition.timestamp
    )

    private fun duration(domainMotion: DomainMotion): Double =
        (domainMotion.endTime - domainMotion.startTime).toDouble() / 1000
}