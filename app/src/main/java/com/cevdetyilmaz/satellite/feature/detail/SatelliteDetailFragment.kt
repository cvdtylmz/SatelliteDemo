package com.cevdetyilmaz.satellite.feature.detail

import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.cevdetyilmaz.satellite.R
import com.cevdetyilmaz.satellite.base.BaseFragment
import com.cevdetyilmaz.satellite.common.partialBold
import com.cevdetyilmaz.satellite.common.viewBinding
import com.cevdetyilmaz.satellite.databinding.FragmentSatelliteDetailBinding
import com.cevdetyilmaz.satellite.domain.model.SatelliteDetailUIModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


/**
 * Created by Cevdet Yılmaz on 20.02.2022
 */

@AndroidEntryPoint
class SatelliteDetailFragment : BaseFragment<SatelliteDetailViewModel>(R.layout.fragment_satellite_detail) {

    override val binding by viewBinding(FragmentSatelliteDetailBinding::bind)
    override val viewModel: SatelliteDetailViewModel by viewModels()
    private val args: SatelliteDetailFragmentArgs by navArgs()

    override fun observeViewModel(viewModel: SatelliteDetailViewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.satelliteDetailFlow.collect {
                    when (it) {
                        SatelliteDetailEvent.Idle -> {
                            // no oop
                        }
                        SatelliteDetailEvent.Loading -> {
                            binding.progressBar.visibility = VISIBLE
                        }

                        is SatelliteDetailEvent.DataLoaded -> {
                            updateUI(it.satelliteDetailUIModel)
                            viewModel.changePosition(it.satelliteDetailUIModel?.lastPosition)
                            binding.progressBar.visibility = GONE
                        }
                        is SatelliteDetailEvent.PositionChange -> {
                            binding.txtSatelliteLastPosition.partialBold(
                                boldString = resources.getString(R.string.satellite_last_position),
                                regularText = "(".plus(
                                    it.position?.posX.toString().plus(it.position?.posY.toString())
                                ).plus(")")
                            )
                            binding.progressBar.visibility = GONE
                        }
                        is SatelliteDetailEvent.Failure -> {
                            Toast.makeText(
                                context,
                                it.error,
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.i("TAG", "observeViewModel:${it.error} ")
                            binding.progressBar.visibility = GONE
                        }
                    }
                }

            }
        }

    }

    override fun initUI() {
        viewModel.getSatelliteDetail(args.satelliteId)
    }

    private fun updateUI(data: SatelliteDetailUIModel?) {
        with(binding) {
            txtSatelliteDetailName.text = args.satelliteName
            txtSatelliteDate.text = data?.dateText
            txtSatelliteHeightMass.partialBold(
                resources.getString(R.string.satellite_height_mass),
                data?.heightMassText
            )
            txtSatelliteCost.partialBold(
                boldString = resources.getString(R.string.satellite_cost),
                data?.costText
            )
            txtSatelliteLastPosition.partialBold(
                boldString = resources.getString(R.string.satellite_last_position),
                regularText = ("(").plus(
                    data?.lastPosition?.get(0)?.positions?.get(0)?.posX.toString()
                        .plus(data?.lastPosition?.get(0)?.positions?.get(0)?.posY.toString())
                        .plus(")")
                )
            )
        }
    }
}