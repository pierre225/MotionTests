package com.pierre.ui.di

import com.pierre.ui.report.mapper.ReportMapper
import com.pierre.ui.square.mapper.CaptureMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UiModule {

    @Provides
    fun captureMapper() = CaptureMapper()

    @Provides
    fun reportMapper() = ReportMapper()
}
