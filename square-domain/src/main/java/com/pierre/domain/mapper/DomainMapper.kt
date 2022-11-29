package com.pierre.domain.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.pierre.data.repository.models.DataMotion
import com.pierre.data.repository.models.DataPosition
import com.pierre.domain.models.DomainMotion
import com.pierre.domain.models.DomainPosition

class DomainMapper {

    fun toDomain(dataMotion: DataMotion) = DomainMotion(
        positions = dataMotion.positions.map { toDomainPosition(it) },
        startTime = dataMotion.startTime,
        endTime = dataMotion.endTime,
        exceeded = dataMotion.exceeded
    )

    fun toData(domainMotion: DomainMotion) = DataMotion(
        positions = domainMotion.positions.map { toDataPosition(it) },
        startTime = domainMotion.startTime,
        endTime = domainMotion.endTime,
        exceeded = domainMotion.exceeded
    )

    private fun toDomainPosition(dataPosition: DataPosition) =
        DomainPosition(
            squareX = dataPosition.squareX,
            squareY = dataPosition.squareY,
            touchX = dataPosition.touchX,
            touchY = dataPosition.touchY,
            timestamp = dataPosition.timestamp
        )

    private fun toDataPosition(domainPosition: DomainPosition) =
        DataPosition(
            squareX = domainPosition.squareX,
            squareY = domainPosition.squareY,
            touchX = domainPosition.touchX,
            touchY = domainPosition.touchY,
            timestamp = domainPosition.timestamp
        )

    fun mapPagingDataToDomain(pagingData: PagingData<out DataMotion>) =
        pagingData.map { toDomain(it) }

}