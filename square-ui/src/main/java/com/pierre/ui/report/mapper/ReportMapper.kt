package com.pierre.ui.report.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.pierre.domain.models.DomainMotion
import com.pierre.domain.models.DomainPosition
import com.pierre.ui.report.models.ReportMotion
import com.pierre.ui.report.models.ReportPosition
import com.pierre.ui.utils.DateUtils

class ReportMapper {

    fun mapPagingDataToReport(pagingData: PagingData<DomainMotion>) =
        pagingData.map { toReport(it) }

    private fun toReport(domainMotion: DomainMotion) = ReportMotion(
        date = DateUtils.convertLongToTime(domainMotion.startTime),
        duration = duration(domainMotion),
        exceeded = domainMotion.exceeded,
        positionCount = domainMotion.positions.size,
        positions = domainMotion.positions.map { toReportPosition(it) }
    )

    fun toReportPosition(domainPosition: DomainPosition) = ReportPosition(
        squareX = domainPosition.squareX,
        squareY = domainPosition.squareY,
        touchX = domainPosition.touchX,
        touchY = domainPosition.touchY,
        timestamp = domainPosition.timestamp
    )

    private fun duration(domainMotion: DomainMotion): Double =
        (domainMotion.endTime - domainMotion.startTime).toDouble() / 1000
}