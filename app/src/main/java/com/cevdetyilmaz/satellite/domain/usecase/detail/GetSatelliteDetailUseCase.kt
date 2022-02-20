package com.cevdetyilmaz.satellite.domain.usecase.detail

import com.cevdetyilmaz.satellite.common.Constant.SATELLITE_DETAIL_FILE
import com.cevdetyilmaz.satellite.common.Resource
import com.cevdetyilmaz.satellite.data.model.entity.SatelliteDetail
import com.cevdetyilmaz.satellite.di.module.dispatcher.DispatcherAnnotation.IoDispatcher
import com.cevdetyilmaz.satellite.domain.repository.SatelliteRepository
import com.cevdetyilmaz.satellite.domain.usecase.base.SingleUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Created by Cevdet Yılmaz on 20.02.2022
 */

class GetSatelliteDetailUseCase @Inject constructor(
    private val repository: SatelliteRepository,
    private val insertSatelliteDetailUseCase: InsertSatelliteDetailUseCase,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : SingleUseCase<GetSatelliteDetailUseCase.Param, List<SatelliteDetail?>?>(dispatcher) {

    override suspend fun execute(params: Param): Resource<List<SatelliteDetail?>?> {
        return try {
            when (val satelliteDetailList =
                repository.getSatelliteDetail(SATELLITE_DETAIL_FILE, params.satelliteId)) {
                is Resource.Success -> {
                    insertSatelliteDetailUseCase.execute(
                        InsertSatelliteDetailUseCase.Param(
                            satelliteDetailList.data?.find { it?.satelliteId == params.satelliteId })
                    )
                    Resource.Success(satelliteDetailList.data)
                }
                is Resource.Failure -> Resource.Failure(satelliteDetailList.error)
            }
        } catch (e: Exception) {
            Resource.Failure(e.toString())
        }
    }

    data class Param(val satelliteId: Int)

}