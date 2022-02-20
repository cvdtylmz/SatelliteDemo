package com.cevdetyilmaz.satellite.domain.usecase.list

import com.cevdetyilmaz.satellite.common.Resource
import com.cevdetyilmaz.satellite.di.module.dispatcher.DispatcherAnnotation.IoDispatcher
import com.cevdetyilmaz.satellite.domain.mapper.list.SatelliteUIModelMapper
import com.cevdetyilmaz.satellite.domain.model.SatelliteUIModel
import com.cevdetyilmaz.satellite.domain.repository.SatelliteRepository
import com.cevdetyilmaz.satellite.domain.usecase.base.SingleUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Created by Cevdet Yılmaz on 19.02.2022
 */

class SatelliteListUseCase @Inject constructor(
    private val repository: SatelliteRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : SingleUseCase<SatelliteListUseCase.Param, List<SatelliteUIModel?>?>(dispatcher) {

    override suspend fun execute(params: Param): Resource<List<SatelliteUIModel?>?> {
        return try {
            when (val satellites = repository.getSatellites(params.fileName)) {
                is Resource.Success -> Resource.Success(
                    SatelliteUIModelMapper.satelliteFileResponseToUIModel(
                        satellites.data
                    )
                )
                is Resource.Failure -> Resource.Failure(satellites.error)
            }
        } catch (e: Exception) {
            Resource.Failure(e.toString())
        }
    }

    data class Param(val fileName: String)

}