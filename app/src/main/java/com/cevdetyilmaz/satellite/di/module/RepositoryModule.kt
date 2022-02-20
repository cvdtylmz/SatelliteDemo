package com.cevdetyilmaz.satellite.di.module

import com.cevdetyilmaz.satellite.data.repository.SatelliteRepositoryImpl
import com.cevdetyilmaz.satellite.domain.repository.SatelliteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Cevdet Yılmaz on 19.02.2022
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindSatelliteRepository(repository: SatelliteRepositoryImpl): SatelliteRepository
}