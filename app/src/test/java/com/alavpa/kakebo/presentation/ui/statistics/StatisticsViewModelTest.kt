package com.alavpa.kakebo.presentation.ui.statistics

import com.alavpa.kakebo.domain.usecases.GetAllLines
import com.alavpa.kakebo.domain.usecases.GetSavings
import com.alavpa.kakebo.domain.usecases.SetSavings
import com.alavpa.kakebo.presentation.mappers.LineUIMapper
import com.alavpa.kakebo.utils.AmountUtils
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class StatisticsViewModelTest {

    private lateinit var viewmodel: StatisticsViewModel
    private val getAllLines: GetAllLines = mockk()
    private val amountUtils: AmountUtils = mockk()
    private val setSavings: SetSavings = mockk()
    private val getSavings: GetSavings = mockk()
    private val linesUIMapper: LineUIMapper = mockk()

    @Before
    fun setUp() {
        viewmodel = provideViewModel(StatisticsState.INITIAL)
    }

    @Test
    fun `when initialize should set proper values`(){
        viewmodel.onInitializeOnce()
    }
    private fun provideViewModel(state: StatisticsState) = StatisticsViewModel(
        getAllLines,
        amountUtils,
        setSavings,
        getSavings,
        linesUIMapper,
        state
    )
}