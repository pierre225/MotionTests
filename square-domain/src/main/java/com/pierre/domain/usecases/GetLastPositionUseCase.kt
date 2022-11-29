package com.pierre.domain.usecases

import com.pierre.data.repository.MotionRepository
import com.pierre.domain.mapper.DomainMapper
import com.pierre.domain.models.DomainPosition

interface GetLastPositionUseCase : suspend () -> DomainPosition?

internal class GetLastPositionUseCaseImpl(
    private val motionRepository: MotionRepository,
    private val mapper: DomainMapper,
) : GetLastPositionUseCase {

    override suspend fun invoke(): DomainPosition? =
        motionRepository.getLastPosition()?.let { mapper.toDomainPosition(it) }
}