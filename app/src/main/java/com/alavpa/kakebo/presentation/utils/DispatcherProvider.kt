package com.alavpa.kakebo.presentation.utils

import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {

    fun get(): CoroutineDispatcher
}

class CoroutineDispatcherProvider @Inject constructor() : DispatcherProvider {
    override fun get(): CoroutineDispatcher = Dispatchers.IO
}
