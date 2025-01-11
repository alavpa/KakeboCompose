package com.alavpa.kakebo.testutils

import com.alavpa.kakebo.presentation.utils.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

class TestCoroutinesProvider : DispatcherProvider {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun get(): CoroutineDispatcher = UnconfinedTestDispatcher()
}
