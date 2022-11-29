package com.pierre.domain.di

import com.pierre.data.repository.MotionRepository
import com.pierre.domain.mapper.DomainMapper
import com.pierre.domain.usecases.*
import com.pierre.domain.usecases.GetPagedMotionsUseCaseImpl
import com.pierre.domain.usecases.InsertMotionUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun domainMapper() = DomainMapper()

    @Provides
    fun insertMotionUseCase(
        repository: MotionRepository,
        mapper: DomainMapper,
    ): InsertMotionUseCase = InsertMotionUseCaseImpl(repository, mapper)

    @Provides
    fun getMotionsUseCase(
        repository: MotionRepository,
        mapper: DomainMapper,
    ): GetMotionsUseCase = GetPagedMotionsUseCaseImpl(repository, mapper)

    @Provides
    fun getLastPositionUseCase(
        repository: MotionRepository,
        mapper: DomainMapper,
    ): GetLastPositionUseCase = GetLastPositionUseCaseImpl(repository, mapper)
}
