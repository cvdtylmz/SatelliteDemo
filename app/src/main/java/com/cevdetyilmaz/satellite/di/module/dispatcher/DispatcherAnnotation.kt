package com.cevdetyilmaz.satellite.di.module.dispatcher

import javax.inject.Qualifier

class DispatcherAnnotation {
    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class DefaultDispatcher

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class IoDispatcher

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class MainDispatcher
}