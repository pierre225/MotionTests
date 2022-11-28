package com.pierre.data.di

import android.content.Context
import androidx.room.Room
import com.pierre.data.repository.MotionRepository
import com.pierre.data.repository.MotionRepositoryImpl
import com.pierre.data.room.AppDatabase
import com.pierre.data.room.dao.MotionDao
import com.pierre.data.room.datasource.RoomDataSource
import com.pierre.data.room.datasource.RoomDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataModule {

    @Provides
    @Singleton
    fun repository(
        roomDataSource: RoomDataSource
    ): MotionRepository = MotionRepositoryImpl(roomDataSource)

    @Provides
    @Singleton
    fun roomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "motion-db"
        ).build()

    @Provides
    @Singleton
    fun motionDao(appDatabase: AppDatabase) =
        appDatabase.motionDao()

    @Provides
    @Singleton
    fun roomDataSource(
        motionDao: MotionDao,
    ): RoomDataSource = RoomDataSourceImpl(motionDao)
}
