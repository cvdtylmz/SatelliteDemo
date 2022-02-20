package com.cevdetyilmaz.satellite.domain.usecase.base

import com.cevdetyilmaz.satellite.common.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Created by Cevdet Yılmaz on 19.02.2022
 */

abstract class SingleUseCase<in Params, Type> constructor(private val dispatcher: CoroutineDispatcher) {

    abstract suspend fun execute(params: Params): Resource<Type>
    suspend operator fun invoke(params: Params): Resource<Type> {
        return withContext(dispatcher) {
            execute(params)
        }
    }
}