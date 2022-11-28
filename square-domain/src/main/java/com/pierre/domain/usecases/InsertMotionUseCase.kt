package com.pierre.domain.usecases

import com.pierre.data.repository.MotionRepository
import com.pierre.domain.mapper.DomainMapper
import com.pierre.domain.models.DomainMotion

interface InsertMotionUseCase : suspend (DomainMotion) -> Unit

internal class InsertMotionUseCaseImpl(
    private val motionRepository: MotionRepository,
    private val mapper: DomainMapper,
) : InsertMotionUseCase {

    override suspend fun invoke(domainMotion: DomainMotion) =
        motionRepository.insertMotion(mapper.toData(domainMotion))

}