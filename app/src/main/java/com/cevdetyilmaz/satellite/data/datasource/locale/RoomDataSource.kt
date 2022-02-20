package com.cevdetyilmaz.satellite.data.datasource.locale

import com.cevdetyilmaz.satellite.common.Resource
import com.cevdetyilmaz.satellite.data.model.entity.SatelliteDetail
import com.cevdetyilmaz.satellite.data.model.entity.SatellitePosition

/**
 * Created by Cevdet Yılmaz on 19.02.2022
 */
interface RoomDataSource {

    suspend fun getSatelliteDetails(): Resource<List<SatelliteDetail>?>
    suspend fun getSatellitePositions(): Resource<List<SatellitePosition>?>
    suspend fun insertSatelliteDetail(satelliteDetail: SatelliteDetail)
    suspend fun insertSatellitePosition(satellitePosition: SatellitePosition)
    suspend fun deleteSatelliteDetail(satelliteDetail: SatelliteDetail)
    suspend fun deleteSatellitePosition(satellitePosition: SatellitePosition)
}