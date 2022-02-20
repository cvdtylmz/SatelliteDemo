package com.cevdetyilmaz.satellite.domain.usecase.list

import com.cevdetyilmaz.satellite.common.Resource
import com.cevdetyilmaz.satellite.di.module.dispatcher.DispatcherAnnotation.IoDispatcher
import com.cevdetyilmaz.satellite.domain.model.SatelliteUIModel
import com.cevdetyilmaz.satellite.domain.usecase.base.SingleUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Created by Cevdet Yılmaz on 19.02.2022
 */

class SatelliteSearchUseCase @Inject constructor(
    private val satelliteListUseCase: SatelliteListUseCase,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : SingleUseCase<SatelliteSearchUseCase.Param, List<SatelliteUIModel?>?>(dispatcher) {

    override suspend fun execute(params: Param): Resource<List<SatelliteUIModel?>?> {
        return try {
            when (val fileList =
                satelliteListUseCase.execute(SatelliteListUseCase.Param(params.fileName))) {
                is Resource.Success -> Resource.Success(
                    fileList.data?.filter {
                        it?.name?.lowercase()?.contains(
                            params.searchKey.lowercase()
                        ) == true
                    }
                )
                is Resource.Failure -> Resource.Failure(fileList.error)
            }
        } catch (e: Exception) {
            Resource.Failure(e.toString())
        }
    }

    data class Param(val fileName: String, val searchKey: String)
}