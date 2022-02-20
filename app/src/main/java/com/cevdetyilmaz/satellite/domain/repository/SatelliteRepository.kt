package com.cevdetyilmaz.satellite.domain.repository

import com.cevdetyilmaz.satellite.common.Resource
import com.cevdetyilmaz.satellite.data.model.entity.SatelliteDetail
import com.cevdetyilmaz.satellite.data.model.entity.SatellitePosition
import com.cevdetyilmaz.satellite.data.model.response.Satellite
import com.cevdetyilmaz.satellite.domain.model.SatelliteDetailUIModel

/**
 * Created by Cevdet Yılmaz on 19.02.2022
 */

interface SatelliteRepository {

    suspend fun getSatellites(fileName: String): Resource<List<Satellite>?>
    suspend fun getSatelliteDetail(
        fileName: String,
        satelliteId: Int
    ): Resource<List<SatelliteDetail?>?>

    suspend fun getSatellitePositions(
        fileName: String,
        satelliteId: Int
    ): Resource<List<SatellitePosition?>?>

    suspend fun insertSatelliteDetail(satelliteDetail: SatelliteDetail)
    suspend fun insertSatellitePosition(satellitePosition: SatellitePosition)
}