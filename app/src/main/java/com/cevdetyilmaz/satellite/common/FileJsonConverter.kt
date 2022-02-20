package com.cevdetyilmaz.satellite.common

import android.content.Context
import com.cevdetyilmaz.satellite.common.Constant.IO_ERROR
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Type

/**
 * Created by Cevdet Yılmaz on 19.02.2022
 */
fun inputStreamToString(inputStream: InputStream): String {
    return try {
        val bytes = ByteArray(inputStream.available())
        inputStream.read(bytes, 0, bytes.size)
        String(bytes)
    } catch (e: IOException) {
        IO_ERROR
    }
}

inline fun <reified T> Context.getListFromJson(
    jsonFileName: String,
    gson: Gson,
    listType: Type
): Resource<T> {
    val myJson = inputStreamToString(this.assets.open(jsonFileName))
    return if (myJson == IO_ERROR) Resource.Failure(IO_ERROR)
    else Resource.Success(gson.fromJson<T>(myJson, listType))
}

inline fun <reified T> Context.getObjectFromJson(jsonFileName: String, gson: Gson): Resource<T> {
    val myJson = inputStreamToString(this.assets.open(jsonFileName))
    return if (myJson == IO_ERROR) Resource.Failure(IO_ERROR)
    else Resource.Success(gson.fromJson(myJson, T::class.java))
}

