package com.cevdetyilmaz.satellite.model

import com.cevdetyilmaz.satellite.R
import com.cevdetyilmaz.satellite.common.Constant
import com.cevdetyilmaz.satellite.common.Resource
import com.cevdetyilmaz.satellite.data.model.response.Satellite
import com.cevdetyilmaz.satellite.domain.model.SatelliteUIModel
import com.cevdetyilmaz.satellite.domain.model.SatelliteUIModelItem
import com.cevdetyilmaz.satellite.feature.list.SatelliteListEvent

/**
 * Created by Cevdet Yılmaz on 20.02.2022
 */
object DummySatelliteListResponse {

    const val FILE_NAME = Constant.SATELLITE_FILE

    private val satelliteResponse1 = Satellite(
        satelliteId = 1,
        active = false,
        name = "Starship-1"
    )

    private val satelliteResponse2 = Satellite(
        satelliteId = 2,
        active = true,
        name = "Dragon-1"
    )

    private val satelliteResponse3 = Satellite(
        satelliteId = 1,
        active = false,
        name = "Starship-2"
    )

    private val satelliteListResponse =
        listOf(satelliteResponse1, satelliteResponse2, satelliteResponse3)

    val getSatelliteList = Resource.Success(satelliteListResponse)
    val getSatelliteListEmpty = Resource.Success(listOf<Satellite>())
    val getSatelliteListError = Resource.Failure("Error")

    val successAndHaveSatellites = SatelliteListEvent.ListLoaded(
        listOf(
            SatelliteUIModelItem(
                data = SatelliteUIModel(
                    satelliteId = satelliteResponse1.satelliteId,
                    name = satelliteResponse1.name,
                    activeText = "Passive",
                    activeImg = R.drawable.ic_passive_circle
                ),
                clickAction = { (it!!) }
            ),
            SatelliteUIModelItem(
                data = SatelliteUIModel(
                    satelliteId = satelliteResponse2.satelliteId,
                    name = satelliteResponse2.name,
                    activeText = "Active",
                    activeImg = R.drawable.ic_active_circle
                ),
                clickAction = { (it!!) }
            ),
            SatelliteUIModelItem(
                data = SatelliteUIModel(
                    satelliteId = satelliteResponse3.satelliteId,
                    name = satelliteResponse3.name,
                    activeText = "Active",
                    activeImg = R.drawable.ic_active_circle
                ),
                clickAction = { (it!!) }
            )
        ))
    val successAndNoDataResponse = SatelliteListEvent.ListLoaded(listOf())
}