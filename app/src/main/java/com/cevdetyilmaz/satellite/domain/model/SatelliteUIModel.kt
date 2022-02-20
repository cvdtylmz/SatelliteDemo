package com.cevdetyilmaz.satellite.domain.model

import androidx.annotation.DrawableRes

/**
 * Created by Cevdet Yılmaz on 19.02.2022
 */

data class SatelliteUIModel(
    val satelliteId : Int?,
    val name: String?,
    val activeText: String?,
    @DrawableRes val activeImg: Int
)
