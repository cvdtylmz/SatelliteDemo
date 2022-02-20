package com.cevdetyilmaz.satellite.domain.usecase.detail

import com.cevdetyilmaz.satellite.common.Resource
import com.cevdetyilmaz.satellite.di.module.dispatcher.DispatcherAnnotation.IoDispatcher
import com.cevdetyilmaz.satellite.domain.mapper.detail.SatelliteDetailUIModelMapper
import com.cevdetyilmaz.satellite.domain.model.SatelliteDetailUIModel
import com.cevdetyilmaz.satellite.domain.usecase.base.SingleUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Created by Cevdet Yılmaz on 20.02.2022
 */

class GetSatelliteUIUseCase @Inject constructor(
    private val getSatelliteDetailUseCase: GetSatelliteDetailUseCase,
    private val getPositionUseCase: GetPositionUseCase,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : SingleUseCase<GetSatelliteUIUseCase.Param, SatelliteDetailUIModel?>(dispatcher) {
    override suspend fun execute(params: Param): Resource<SatelliteDetailUIModel?> {
        return try {
            when (val satelliteDetail =
                getSatelliteDetailUseCase.execute(GetSatelliteDetailUseCase.Param(params.satelliteId))) {
                is Resource.Success -> {
                    when (val positions =
                        getPositionUseCase.execute(GetPositionUseCase.Param(params.satelliteId))) {
                        is Resource.Success -> {
                            Resource.Success(
                                SatelliteDetailUIModelMapper.satelliteDetailToUIModel(
                                    satelliteDetail.data,
                                    positions.data
                                )?.find { it?.satelliteId == params.satelliteId }
                            )
                        }
                        is Resource.Failure -> Resource.Failure(positions.error)
                    }
                }
                is Resource.Failure -> Resource.Failure(satelliteDetail.error)
            }

        } catch (e: Exception) {
            Resource.Failure(e.toString())
        }
    }

    data class Param(val satelliteId: Int)
}