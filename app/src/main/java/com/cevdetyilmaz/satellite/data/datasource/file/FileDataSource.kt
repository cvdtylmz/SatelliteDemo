package com.cevdetyilmaz.satellite.data.datasource.file

import com.cevdetyilmaz.satellite.common.Resource
import com.cevdetyilmaz.satellite.data.model.entity.SatelliteDetail
import com.cevdetyilmaz.satellite.data.model.entity.SatellitePosition
import com.cevdetyilmaz.satellite.data.model.response.Satellite
import com.cevdetyilmaz.satellite.data.model.response.SatellitePositionsList

/**
 * Created by Cevdet Yılmaz on 19.02.2022
 */
interface FileDataSource {
    suspend fun getSatelliteList(fileName: String): Resource<List<Satellite>?>
    suspend fun getSatelliteDetail(fileName: String): Resource<List<SatelliteDetail>?>
    suspend fun getSatellitePosition(fileName: String): Resource<SatellitePositionsList?>
}