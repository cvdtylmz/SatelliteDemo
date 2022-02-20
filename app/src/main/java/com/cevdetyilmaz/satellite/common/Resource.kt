package com.cevdetyilmaz.satellite.common

/**
 * Created by Cevdet Yılmaz on 19.02.2022
 */
sealed class Resource<out R> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val error: String) : Resource<Nothing>()
}