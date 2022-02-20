package com.cevdetyilmaz.satellite.domain.usecase.detail

import com.cevdetyilmaz.satellite.common.Resource
import com.cevdetyilmaz.satellite.data.model.entity.SatellitePosition
import com.cevdetyilmaz.satellite.di.module.dispatcher.DispatcherAnnotation
import com.cevdetyilmaz.satellite.domain.repository.SatelliteRepository
import com.cevdetyilmaz.satellite.domain.usecase.base.SingleUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Created by Cevdet Yılmaz on 20.02.2022
 */

class InsertSatellitePositionUseCase @Inject constructor(
    private val repository: SatelliteRepository,
    @DispatcherAnnotation.IoDispatcher dispatcher: CoroutineDispatcher
) : SingleUseCase<InsertSatellitePositionUseCase.Param, Unit>(dispatcher) {
    override suspend fun execute(params: Param): Resource<Unit> {
        return try {
            params.satellitePosition?.let { repository.insertSatellitePosition(it) }
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failure(e.toString())
        }
    }

    data class Param(val satellitePosition: SatellitePosition?)
}