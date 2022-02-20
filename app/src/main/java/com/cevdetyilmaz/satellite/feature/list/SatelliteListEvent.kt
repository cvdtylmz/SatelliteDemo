package com.cevdetyilmaz.satellite.feature.list

import com.cevdetyilmaz.satellite.domain.model.SatelliteUIModelItem


/**
 * Created by Cevdet Yılmaz on 19.02.2022
 */

sealed class SatelliteListEvent {
    object Idle : SatelliteListEvent()
    object Loading : SatelliteListEvent()
    class ListLoaded(val list: List<SatelliteUIModelItem>?) : SatelliteListEvent()
    class Failure(val errorName: String) : SatelliteListEvent()
}