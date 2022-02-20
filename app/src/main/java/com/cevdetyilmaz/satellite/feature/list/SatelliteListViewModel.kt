package com.cevdetyilmaz.satellite.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cevdetyilmaz.satellite.common.Constant.SATELLITE_FILE
import com.cevdetyilmaz.satellite.common.Resource
import com.cevdetyilmaz.satellite.domain.model.SatelliteListArgumentModel
import com.cevdetyilmaz.satellite.domain.model.SatelliteUIModel
import com.cevdetyilmaz.satellite.domain.model.SatelliteUIModelItem
import com.cevdetyilmaz.satellite.domain.usecase.list.SatelliteListUseCase
import com.cevdetyilmaz.satellite.domain.usecase.list.SatelliteSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Cevdet Yılmaz on 19.02.2022
 */

@HiltViewModel
class SatelliteListViewModel @Inject constructor(
    private val satelliteListUseCase: SatelliteListUseCase,
    private val satelliteListSearchUseCase: SatelliteSearchUseCase
) : ViewModel() {

    private val _listFlow = MutableStateFlow<SatelliteListEvent>(SatelliteListEvent.Idle)
    val listFlow: StateFlow<SatelliteListEvent> get() = _listFlow

    private val _listClickedFlow = MutableSharedFlow<SatelliteListArgumentModel>()
    val listClickedFlow: SharedFlow<SatelliteListArgumentModel> get() = _listClickedFlow

    fun getSatelliteList() {
        _listFlow.value = SatelliteListEvent.Loading
        viewModelScope.launch {
            when (val satelliteList =
                satelliteListUseCase.execute(SatelliteListUseCase.Param(SATELLITE_FILE))) {
                is Resource.Success -> {
                    _listFlow.value =
                        SatelliteListEvent.ListLoaded(addActionsToList(satelliteList.data))
                }
                is Resource.Failure -> {
                    SatelliteListEvent.Failure(satelliteList.error)
                }
            }
        }
    }

    fun makeSearch(searchKey: String) {
        _listFlow.value = SatelliteListEvent.Loading
        viewModelScope.launch {
            when (val hasSearchedList =
                satelliteListSearchUseCase.execute(
                    SatelliteSearchUseCase.Param(
                        SATELLITE_FILE,
                        searchKey
                    )
                )) {
                is Resource.Success -> {
                    _listFlow.value =
                        SatelliteListEvent.ListLoaded(addActionsToList(hasSearchedList.data))
                }
                is Resource.Failure -> {
                    SatelliteListEvent.Failure(hasSearchedList.error)
                }
            }
        }
    }

    private fun addActionsToList(list: List<SatelliteUIModel?>?): List<SatelliteUIModelItem>? {
        return list?.map {
            SatelliteUIModelItem(data = it) { arguments ->
                viewModelScope.launch {
                    arguments?.let { _listClickedFlow.emit(arguments) }
                }
            }
        }
    }
}