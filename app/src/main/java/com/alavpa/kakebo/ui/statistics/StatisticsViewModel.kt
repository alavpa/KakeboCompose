package com.alavpa.kakebo.ui.statistics

import androidx.lifecycle.ViewModel
import com.alavpa.kakebo.ui.models.LineUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class StatisticsViewModel : ViewModel() {

    private val _state = MutableStateFlow(StatisticsState.INITIAL)
    val state: StateFlow<StatisticsState>
        get() = _state

}

data class StatisticsState(
    val income: String,
    val outcome: String,
    val budget: String,
    val savings: String,
    val budgetWithSavings: String,
    val lines: List<LineUI>
) {
    companion object {
        val INITIAL = StatisticsState(
            income = "",
            outcome = "",
            budget = "",
            savings = "",
            budgetWithSavings = "",
            lines = emptyList()
        )
    }
}