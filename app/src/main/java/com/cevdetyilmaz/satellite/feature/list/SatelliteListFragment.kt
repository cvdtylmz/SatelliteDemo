package com.cevdetyilmaz.satellite.feature.list

import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cevdetyilmaz.satellite.R
import com.cevdetyilmaz.satellite.base.BaseFragment
import com.cevdetyilmaz.satellite.common.DividerItemDecorator
import com.cevdetyilmaz.satellite.common.viewBinding
import com.cevdetyilmaz.satellite.databinding.FragmentSatelliteListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


/**
 * Created by Cevdet Yılmaz on 19.02.2022
 */

@AndroidEntryPoint
class SatelliteListFragment : BaseFragment<SatelliteListViewModel>(R.layout.fragment_satellite_list) {

    private var recyclerViewAdapter: SatelliteRecyclerViewAdapter? = null

    override val binding by viewBinding(FragmentSatelliteListBinding::bind)
    override val viewModel: SatelliteListViewModel by viewModels()

    override fun observeViewModel(viewModel: SatelliteListViewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.listFlow.collect {
                    when (it) {
                        SatelliteListEvent.Idle -> {
                            // INITIAL STATE NO-OP
                        }
                        SatelliteListEvent.Loading -> {
                            binding.progressBar.visibility = VISIBLE
                        }

                        is SatelliteListEvent.ListLoaded -> {
                            recyclerViewAdapter?.updateItemList(it.list?.toMutableList())
                            binding.progressBar.visibility = GONE
                        }
                        is SatelliteListEvent.Failure -> {
                            Toast.makeText(context, it.errorName, Toast.LENGTH_SHORT)
                                .show()
                            binding.progressBar.visibility = GONE
                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.listClickedFlow.collect {
                    findNavController().navigate(
                        R.id.action_listFragment_to_detailsFragment,
                        bundleOf(SATELLITE_ID to it.id, SATELLITE_NAME to it.name)
                    )
                }
            }
        }
    }

    override fun initUI() {
        viewModel.getSatelliteList()
        recyclerViewAdapter = SatelliteRecyclerViewAdapter()
        with(binding) {
            rvSatellite.apply {
                layoutManager = LinearLayoutManager(requireContext())
                ContextCompat.getDrawable(context, R.drawable.app_divider)?.let {
                    DividerItemDecorator(
                        it
                    )
                }?.let {
                    addItemDecoration(
                        it
                    )
                }
                adapter = recyclerViewAdapter
            }
            searchBar.apply {
                setHint(requireContext().getString(R.string.search))
                unregisterToSearchTextWatcher()
                registerTextWatcher {
                    run {
                        viewModel.makeSearch(it)
                    }
                }
                setImageButtonListener { viewModel.makeSearch(getSearchText()) }
            }
        }
    }

    companion object {
        const val SATELLITE_ID = "satellite_id"
        const val SATELLITE_NAME = "satellite_name"
    }
}