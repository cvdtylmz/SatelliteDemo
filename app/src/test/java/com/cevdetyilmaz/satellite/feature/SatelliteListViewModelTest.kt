package com.cevdetyilmaz.satellite.feature

import com.cevdetyilmaz.satellite.domain.repository.SatelliteRepository
import com.cevdetyilmaz.satellite.domain.usecase.list.SatelliteListUseCase
import com.cevdetyilmaz.satellite.domain.usecase.list.SatelliteSearchUseCase
import com.cevdetyilmaz.satellite.feature.list.SatelliteListEvent
import com.cevdetyilmaz.satellite.feature.list.SatelliteListViewModel
import com.cevdetyilmaz.satellite.model.DummySatelliteListResponse
import com.cevdetyilmaz.satellite.util.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Cevdet Yılmaz on 20.02.2022
 */

@ExperimentalCoroutinesApi
class SatelliteListViewModelTest {
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val mainTestCoroutineRule = MainCoroutineRule()

    private lateinit var satelliteListViewModel: SatelliteListViewModel
    private lateinit var satelliteListUseCase: SatelliteListUseCase
    private lateinit var satelliteListSearchUseCase: SatelliteSearchUseCase

    private var satelliteRepository = mockk<SatelliteRepository>()

    @Before
    fun setUp() {
        satelliteListUseCase =
            SatelliteListUseCase(satelliteRepository, testDispatcher)
        satelliteListSearchUseCase = SatelliteSearchUseCase(satelliteListUseCase, testDispatcher)
        satelliteListViewModel =
            SatelliteListViewModel(satelliteListUseCase, satelliteListSearchUseCase)
    }

    @Test
    fun `check that when execute getSatelliteList function getSatelliteList must be ListLoaded state of SatelliteListEvent`() =
        mainTestCoroutineRule.runBlockingTest {
            coEvery { satelliteRepository.getSatellites(DummySatelliteListResponse.FILE_NAME) } returns DummySatelliteListResponse.getSatelliteList
            satelliteListViewModel.getSatelliteList()
            val result = satelliteListViewModel.listFlow.first()
            assertThat(result).isInstanceOf(SatelliteListEvent.ListLoaded::class.java)
            result as SatelliteListEvent.ListLoaded
            assertThat(result.list?.size).isEqualTo(
                DummySatelliteListResponse
                    .successAndHaveSatellites
                    .list?.size
            )
        }

    @Test
    fun `check that when execute getSatelliteList function with has not satellite getSatelliteList must be ListLoaded state of TaxiPreviousTripListEvent`() =
        mainTestCoroutineRule.runBlockingTest {
            coEvery { satelliteRepository.getSatellites(DummySatelliteListResponse.FILE_NAME) } returns DummySatelliteListResponse.getSatelliteListEmpty
            satelliteListViewModel.getSatelliteList()
            val result = satelliteListViewModel.listFlow.first()
            assertThat(result).isInstanceOf(SatelliteListEvent.ListLoaded::class.java)
            result as SatelliteListEvent.ListLoaded
            assertThat(result.list?.size).isEqualTo(
                DummySatelliteListResponse
                    .successAndNoDataResponse
                    .list?.size
            )
        }

    @Test
    fun `check that when execute getSatelliteList function with error response, getSatelliteList must be Failure state of SatelliteListEvent`() =
        mainTestCoroutineRule.runBlockingTest {
            coEvery { satelliteRepository.getSatellites(DummySatelliteListResponse.FILE_NAME) } returns DummySatelliteListResponse.getSatelliteListError
            satelliteListViewModel.getSatelliteList()
            val result = satelliteListViewModel.listFlow.first()
            assertThat(result).isInstanceOf(SatelliteListEvent.Failure::class.java)
        }
}