package com.cevdetyilmaz.satellite.domain.model

/**
 * Created by Cevdet Yılmaz on 19.02.2022
 */

data class SatelliteUIModelItem(val data: SatelliteUIModel?, val clickAction: ((SatelliteListArgumentModel?) -> Unit)?)