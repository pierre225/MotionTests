package com.pierre.ui.di

import com.pierre.ui.mapper.UiMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UiModule {

    @Provides
    fun uiMapper() = UiMapper()

}
