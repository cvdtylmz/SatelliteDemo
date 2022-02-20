package com.cevdetyilmaz.satellite.data.repository

import com.cevdetyilmaz.satellite.common.Constant.SATELLITE_DETAIL_FILE
import com.cevdetyilmaz.satellite.common.Resource
import com.cevdetyilmaz.satellite.data.datasource.file.FileDataSource
import com.cevdetyilmaz.satellite.data.datasource.locale.RoomDataSource
import com.cevdetyilmaz.satellite.data.model.entity.SatelliteDetail
import com.cevdetyilmaz.satellite.data.model.entity.SatellitePosition
import com.cevdetyilmaz.satellite.data.model.response.Satellite
import com.cevdetyilmaz.satellite.domain.repository.SatelliteRepository
import javax.inject.Inject

/**
 * Created by Cevdet Yılmaz on 19.02.2022
 */
class SatelliteRepositoryImpl @Inject constructor(
    private val fileDataSource: FileDataSource,
    private val roomDataSource: RoomDataSource
) : SatelliteRepository {

    override suspend fun getSatellites(fileName: String): Resource<List<Satellite>?> {
        return fileDataSource.getSatelliteList(fileName)
    }

    override suspend fun getSatelliteDetail(
        fileName: String,
        satelliteId: Int
    ): Resource<List<SatelliteDetail?>?> {
        return when (val satelliteDetails = roomDataSource.getSatelliteDetails()) {
            is Resource.Success -> {
                if (satelliteDetails.data?.any { it.satelliteId == satelliteId } == true) {
                    satelliteDetails
                } else {
                    fileDataSource.getSatelliteDetail(SATELLITE_DETAIL_FILE)
                }
            }
            is Resource.Failure -> fileDataSource.getSatelliteDetail(SATELLITE_DETAIL_FILE)
        }
    }

    override suspend fun getSatellitePositions(
        fileName: String,
        satelliteId: Int
    ): Resource<List<SatellitePosition?>?> {
        return when (val satellitePositions = roomDataSource.getSatellitePositions()) {
            is Resource.Success -> {
                if (satellitePositions.data?.any { it.satelliteId == satelliteId } == true) {
                    satellitePositions
                } else {
                    when (val listObject = fileDataSource.getSatellitePosition(fileName)) {
                        is Resource.Success -> Resource.Success(listObject.data?.list)
                        is Resource.Failure -> Resource.Failure(listObject.error)
                    }
                }
            }
            is Resource.Failure -> when (val listObject =
                fileDataSource.getSatellitePosition(fileName)) {
                is Resource.Success -> Resource.Success(listObject.data?.list)
                is Resource.Failure -> Resource.Failure(listObject.error)
            }
        }
    }

    override suspend fun insertSatelliteDetail(satelliteDetail: SatelliteDetail) {
        roomDataSource.deleteSatelliteDetail(satelliteDetail)
        roomDataSource.insertSatelliteDetail(satelliteDetail)
    }

    override suspend fun insertSatellitePosition(satellitePosition: SatellitePosition) {
        roomDataSource.deleteSatellitePosition(satellitePosition)
        roomDataSource.insertSatellitePosition(satellitePosition)
    }
}