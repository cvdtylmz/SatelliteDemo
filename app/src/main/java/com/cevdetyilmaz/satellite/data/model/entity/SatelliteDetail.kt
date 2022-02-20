package com.cevdetyilmaz.satellite.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Cevdet Yılmaz on 19.02.2022
 */
@Entity(tableName = "satellite_detail")
data class SatelliteDetail(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val satelliteId: Int,
    val costPerLaunch: Long,
    val firstFlight: String,
    val height: Int,
    val mass: Long
)
