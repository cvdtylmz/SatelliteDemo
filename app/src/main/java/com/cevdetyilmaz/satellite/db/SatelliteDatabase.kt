package com.cevdetyilmaz.satellite.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cevdetyilmaz.satellite.data.model.entity.SatelliteDetail
import com.cevdetyilmaz.satellite.data.model.entity.SatellitePosition
import com.cevdetyilmaz.satellite.data.model.response.PositionCoordinate

/**
 * Created by Cevdet Yılmaz on 19.02.2022
 */
@Database(
    entities = [SatelliteDetail::class, SatellitePosition::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class SatelliteDatabase : RoomDatabase() {
    abstract fun getSatelliteDao(): SatelliteDao
}