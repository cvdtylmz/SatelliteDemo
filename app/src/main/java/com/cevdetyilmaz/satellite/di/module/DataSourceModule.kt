package com.cevdetyilmaz.satellite.di.module

import android.content.Context
import com.cevdetyilmaz.satellite.data.datasource.file.FileDataSource
import com.cevdetyilmaz.satellite.data.datasource.file.FileDataSourceImpl
import com.cevdetyilmaz.satellite.data.datasource.locale.RoomDataSource
import com.cevdetyilmaz.satellite.data.datasource.locale.RoomDataSourceImpl
import com.cevdetyilmaz.satellite.db.SatelliteDao
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Cevdet Yılmaz on 19.02.2022
 */

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideFileDataSource(
        gson: Gson,
        @ApplicationContext appContext: Context,
    ): FileDataSource = FileDataSourceImpl(gson, appContext)

    @Provides
    @Singleton
    fun provideRoomDataSource(dao: SatelliteDao): RoomDataSource = RoomDataSourceImpl(dao)
}