package com.cevdetyilmaz.satellite.feature.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cevdetyilmaz.satellite.common.viewBinding
import com.cevdetyilmaz.satellite.databinding.ItemSatelliteListBinding
import com.cevdetyilmaz.satellite.domain.model.SatelliteListArgumentModel
import com.cevdetyilmaz.satellite.domain.model.SatelliteUIModelItem

/**
 * Created by Cevdet Yılmaz on 19.02.2022
 */

class SatelliteRecyclerViewAdapter :
    RecyclerView.Adapter<SatelliteRecyclerViewAdapter.SatelliteListViewHolder>() {

    private val dataList: ArrayList<SatelliteUIModelItem> = arrayListOf()

    fun updateItemList(itemList: MutableList<SatelliteUIModelItem>?) {
        this.dataList.clear()
        itemList?.let {
            this.dataList.addAll(itemList)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SatelliteListViewHolder {
        return SatelliteListViewHolder(parent.viewBinding(ItemSatelliteListBinding::inflate))
    }

    override fun onBindViewHolder(holder: SatelliteListViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class SatelliteListViewHolder(private val binding: ItemSatelliteListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SatelliteUIModelItem) {
            with(binding) {
                item.data?.activeImg?.let { imgSatelliteStatus.setImageResource(it) }
                txtSatelliteName.text = item.data?.name
                txtSatelliteStatus.text = item.data?.activeText
                root.setOnClickListener {
                    item.clickAction?.invoke(
                        SatelliteListArgumentModel(
                            item.data?.satelliteId,
                            item.data?.name
                        )
                    )
                }
            }
        }
    }
}
