package com.cevdetyilmaz.satellite.feature.detail

import com.cevdetyilmaz.satellite.data.model.response.PositionCoordinate
import com.cevdetyilmaz.satellite.domain.model.SatelliteDetailUIModel


/**
 * Created by Cevdet Yılmaz on 20.02.2022
 */

sealed class SatelliteDetailEvent {
    object Idle : SatelliteDetailEvent()
    object Loading : SatelliteDetailEvent()
    class DataLoaded(val satelliteDetailUIModel: SatelliteDetailUIModel?) : SatelliteDetailEvent()
    class PositionChange(val position: PositionCoordinate?) : SatelliteDetailEvent()
    class Failure(val error: String?) : SatelliteDetailEvent()
}
