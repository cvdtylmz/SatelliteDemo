package com.cevdetyilmaz.satellite.domain.mapper.detail

import com.cevdetyilmaz.satellite.data.model.entity.SatelliteDetail
import com.cevdetyilmaz.satellite.data.model.entity.SatellitePosition
import com.cevdetyilmaz.satellite.domain.model.SatelliteDetailUIModel
import java.text.NumberFormat

/**
 * Created by Cevdet Yılmaz on 19.02.2022
 */

object SatelliteDetailUIModelMapper {

    fun satelliteDetailToUIModel(
        list: List<SatelliteDetail?>?,
        listPosition: List<SatellitePosition?>?
    ): List<SatelliteDetailUIModel?>? {
        return list?.map { satelliteDetail ->
            SatelliteDetailUIModel(
                satelliteId = satelliteDetail?.satelliteId,
                dateText = satelliteDetail?.firstFlight,
                heightMassText = satelliteDetail?.height.toString().plus("/")
                    .plus(
                        NumberFormat.getNumberInstance().format(satelliteDetail?.mass).toString()
                    ),
                costText = (NumberFormat.getNumberInstance()
                    .format(satelliteDetail?.costPerLaunch)).toString(),
                lastPosition = listPosition?.filter { position -> satelliteDetail?.satelliteId == position?.satelliteId }
            )
        }
    }
}