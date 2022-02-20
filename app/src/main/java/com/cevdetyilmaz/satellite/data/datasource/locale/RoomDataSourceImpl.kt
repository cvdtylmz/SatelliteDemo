package com.cevdetyilmaz.satellite.data.datasource.locale

import com.cevdetyilmaz.satellite.common.Constant.LIST_EMPTY_ERROR
import com.cevdetyilmaz.satellite.common.Resource
import com.cevdetyilmaz.satellite.data.model.entity.SatelliteDetail
import com.cevdetyilmaz.satellite.data.model.entity.SatellitePosition
import com.cevdetyilmaz.satellite.db.SatelliteDao
import javax.inject.Inject

/**
 * Created by Cevdet Yılmaz on 19.02.2022
 */
class RoomDataSourceImpl @Inject constructor(private val dao: SatelliteDao) : RoomDataSource {
    override suspend fun getSatelliteDetails(): Resource<List<SatelliteDetail>?> {
        val satelliteDetails = dao.getSatelliteDetails()
        return if (satelliteDetails?.isNotEmpty() == true) {
            Resource.Success(satelliteDetails)
        } else Resource.Failure(LIST_EMPTY_ERROR)
    }

    override suspend fun getSatellitePositions(): Resource<List<SatellitePosition>?> {
        val satellitePositions = dao.getSatellitePositions()
        return if (satellitePositions?.isNotEmpty() == true) {
            Resource.Success(satellitePositions)
        } else Resource.Failure(LIST_EMPTY_ERROR)
    }

    override suspend fun insertSatelliteDetail(satelliteDetail: SatelliteDetail) {
        dao.insertSatelliteDetail(satelliteDetail)
    }

    override suspend fun insertSatellitePosition(satellitePosition: SatellitePosition) {
        dao.deleteSatellitePosition(satellitePosition)
        dao.insertSatellitePosition(satellitePosition)
    }

    override suspend fun deleteSatelliteDetail(satelliteDetail: SatelliteDetail) {
        dao.deleteSatelliteDetail(satelliteDetail)
    }

    override suspend fun deleteSatellitePosition(satellitePosition: SatellitePosition) {
        dao.deleteSatellitePosition(satellitePosition)
    }
}