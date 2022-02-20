package com.cevdetyilmaz.satellite.di.module

import android.content.Context
import androidx.room.Room
import com.cevdetyilmaz.satellite.db.Converters
import com.cevdetyilmaz.satellite.db.SatelliteDao
import com.cevdetyilmaz.satellite.db.SatelliteDatabase
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
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context,
        typeConverters: Converters
    ): SatelliteDatabase {
        return Room
            .databaseBuilder(appContext, SatelliteDatabase::class.java, "satellite.db")
            .addTypeConverter(typeConverters)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideSatelliteDao(db: SatelliteDatabase): SatelliteDao {
        return db.getSatelliteDao()
    }
}