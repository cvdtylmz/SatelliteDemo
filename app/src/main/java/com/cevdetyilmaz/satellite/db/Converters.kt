package com.cevdetyilmaz.satellite.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.cevdetyilmaz.satellite.data.model.entity.SatellitePosition
import com.cevdetyilmaz.satellite.data.model.response.PositionCoordinate
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

/**
 * Created by Cevdet Yılmaz on 19.02.2022
 */
@ProvidedTypeConverter
class Converters @Inject constructor(private val gson: Gson) {

    @TypeConverter
    fun fromString(value: String?): List<PositionCoordinate?> {
        val listType = object :
            TypeToken<ArrayList<PositionCoordinate?>?>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<PositionCoordinate?>?): String {
        return gson.toJson(list)
    }
}