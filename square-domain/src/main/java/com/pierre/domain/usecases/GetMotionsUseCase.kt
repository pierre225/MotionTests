package com.pierre.domain.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pierre.data.repository.MotionRepository
import com.pierre.domain.mapper.DomainMapper
import com.pierre.domain.models.DomainMotion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GetMotionsUseCase : () -> Flow<PagingData<DomainMotion>>

internal class GetPagedMotionsUseCaseImpl(
    private val motionRepository: MotionRepository,
    private val mapper: DomainMapper,
) : GetMotionsUseCase {

    /**
     * Retrieve PagingData from the repository,
     * It will initially load 100 items and then 25 by 25 while scrolling
     */
    override fun invoke(): Flow<PagingData<DomainMotion>> =
        Pager(
            PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = INITIAL_LOADING_SIZE,
                enablePlaceholders = false
            )
        ) { motionRepository.getPagedMotions() }
            .flow
            .map(mapper::mapPagingDataToDomain)

    companion object {
        private const val PAGE_SIZE = 25
        private const val INITIAL_LOADING_SIZE = 100
    }
}