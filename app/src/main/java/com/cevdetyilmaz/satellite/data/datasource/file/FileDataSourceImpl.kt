package com.cevdetyilmaz.satellite.data.datasource.file

import android.content.Context
import com.cevdetyilmaz.satellite.common.Resource
import com.cevdetyilmaz.satellite.common.getListFromJson
import com.cevdetyilmaz.satellite.common.getObjectFromJson
import com.cevdetyilmaz.satellite.data.model.entity.SatelliteDetail
import com.cevdetyilmaz.satellite.data.model.entity.SatellitePosition
import com.cevdetyilmaz.satellite.data.model.response.Satellite
import com.cevdetyilmaz.satellite.data.model.response.SatellitePositionsList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by Cevdet Yılmaz on 19.02.2022
 */

class FileDataSourceImpl @Inject constructor(
    private val gson: Gson,
    @ApplicationContext private val context: Context
) : FileDataSource {
    override suspend fun getSatelliteList(fileName: String): Resource<List<Satellite>?> {
        return context.getListFromJson(fileName, gson, object : TypeToken<List<Satellite>>() {}.type)
    }

    override suspend fun getSatelliteDetail(fileName: String): Resource<List<SatelliteDetail>?> {
        return context.getListFromJson(fileName, gson,object : TypeToken<List<SatelliteDetail>>() {}.type)
    }

    override suspend fun getSatellitePosition(fileName: String): Resource<SatellitePositionsList?> {
        return context.getObjectFromJson(fileName, gson)
    }
}