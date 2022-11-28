package com.pierre.domain.usecases

import com.pierre.data.repository.MotionRepository
import com.pierre.domain.mapper.DomainMapper
import com.pierre.domain.models.DomainMotion

interface GetMotionsUseCase : suspend () -> List<DomainMotion>

internal class GetMotionsUseCaseImpl(
    private val motionRepository: MotionRepository,
    private val mapper: DomainMapper,
) : GetMotionsUseCase {

    override suspend fun invoke() =
        motionRepository.getMotions().map { mapper.toDomain(it) }

}