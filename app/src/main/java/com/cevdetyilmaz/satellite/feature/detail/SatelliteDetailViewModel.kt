package com.cevdetyilmaz.satellite.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cevdetyilmaz.satellite.common.Constant.POSITION_CHANGE_DELAY
import com.cevdetyilmaz.satellite.common.Resource
import com.cevdetyilmaz.satellite.data.model.entity.SatellitePosition
import com.cevdetyilmaz.satellite.domain.usecase.detail.GetSatelliteUIUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Cevdet Yılmaz on 20.02.2022
 */

@HiltViewModel
class SatelliteDetailViewModel @Inject constructor(
    private val getSatelliteUIUseCase: GetSatelliteUIUseCase
) : ViewModel() {

    private val _satelliteDetailFlow =
        MutableStateFlow<SatelliteDetailEvent>(SatelliteDetailEvent.Idle)
    val satelliteDetailFlow: StateFlow<SatelliteDetailEvent> get() = _satelliteDetailFlow

    fun getSatelliteDetail(satelliteId: Int) {
        _satelliteDetailFlow.value = SatelliteDetailEvent.Loading
        viewModelScope.launch {
            when (val satelliteUIModel =
                getSatelliteUIUseCase.execute(GetSatelliteUIUseCase.Param(satelliteId))) {
                is Resource.Success -> {
                    _satelliteDetailFlow.value =
                        SatelliteDetailEvent.DataLoaded(satelliteUIModel.data)
                }
                is Resource.Failure -> {
                    _satelliteDetailFlow.value =
                        SatelliteDetailEvent.Failure(satelliteUIModel.error)
                }
            }
        }
    }

    fun changePosition(list: List<SatellitePosition?>?) {
        viewModelScope.launch {
            val positions = list?.get(0)?.positions
            for (i in 1 until positions!!.size) {
                delay(POSITION_CHANGE_DELAY)
                _satelliteDetailFlow.value = SatelliteDetailEvent.PositionChange(positions[i])
            }
        }
    }
}