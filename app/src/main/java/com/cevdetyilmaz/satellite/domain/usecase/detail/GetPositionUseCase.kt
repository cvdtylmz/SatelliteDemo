package com.cevdetyilmaz.satellite.domain.usecase.detail

import com.cevdetyilmaz.satellite.common.Constant.SATELLITE_POSITION_FILE
import com.cevdetyilmaz.satellite.common.Resource
import com.cevdetyilmaz.satellite.data.model.entity.SatellitePosition
import com.cevdetyilmaz.satellite.di.module.dispatcher.DispatcherAnnotation.IoDispatcher
import com.cevdetyilmaz.satellite.domain.repository.SatelliteRepository
import com.cevdetyilmaz.satellite.domain.usecase.base.SingleUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Created by Cevdet Yılmaz on 20.02.2022
 */

class GetPositionUseCase @Inject constructor(
    private val repository: SatelliteRepository,
    private val insertSatellitePositionUseCase: InsertSatellitePositionUseCase,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : SingleUseCase<GetPositionUseCase.Param, List<SatellitePosition?>?>(dispatcher) {
    override suspend fun execute(params: Param): Resource<List<SatellitePosition?>?> {
        return try {
            when (val positions =
                repository.getSatellitePositions(SATELLITE_POSITION_FILE, params.satelliteId)) {
                is Resource.Success -> {
                    insertSatellitePositionUseCase.execute(
                        InsertSatellitePositionUseCase.Param(
                            positions.data?.find { it?.satelliteId == params.satelliteId })
                    )
                    Resource.Success(positions.data)
                }
                is Resource.Failure -> Resource.Failure(error = positions.error)
            }
        } catch (e: Exception) {
            Resource.Failure(e.toString())
        }
    }

    data class Param(val satelliteId: Int)
}