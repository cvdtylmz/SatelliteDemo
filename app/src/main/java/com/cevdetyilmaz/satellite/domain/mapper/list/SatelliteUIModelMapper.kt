package com.cevdetyilmaz.satellite.domain.mapper.list

import com.cevdetyilmaz.satellite.R
import com.cevdetyilmaz.satellite.data.model.response.Satellite
import com.cevdetyilmaz.satellite.domain.model.SatelliteUIModel

/**
 * Created by Cevdet Yılmaz on 19.02.2022
 */

object SatelliteUIModelMapper {

    fun satelliteFileResponseToUIModel(list: List<Satellite?>?): List<SatelliteUIModel?>? {
        return list?.map {
            SatelliteUIModel(
                satelliteId = it?.satelliteId,
                name = it?.name,
                activeText = if (it?.active == true) "Active" else "Passive",
                activeImg = if (it?.active == true) (R.drawable.ic_active_circle) else (R.drawable.ic_passive_circle)
            )
        }
    }
}