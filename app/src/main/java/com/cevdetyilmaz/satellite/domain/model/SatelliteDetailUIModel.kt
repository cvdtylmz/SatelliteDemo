package com.cevdetyilmaz.satellite.domain.model

import com.cevdetyilmaz.satellite.data.model.entity.SatellitePosition

/**
 * Created by Cevdet Yılmaz on 19.02.2022
 */

data class SatelliteDetailUIModel(
    val satelliteId: Int?,
    val heightMassText: String?,
    val costText: String?,
    val dateText: String?,
    val lastPosition: List<SatellitePosition?>?
)
