package com.cevdetyilmaz.satellite.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cevdetyilmaz.satellite.data.model.response.PositionCoordinate

/**
 * Created by Cevdet Yılmaz on 19.02.2022
 */
@Entity(tableName = "satellite_position")
data class SatellitePosition(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val satelliteId: Int,
    val positions: List<PositionCoordinate>
)
