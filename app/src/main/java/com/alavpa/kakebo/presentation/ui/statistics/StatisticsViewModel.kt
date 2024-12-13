package com.alavpa.kakebo.presentation.ui.statistics

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alavpa.kakebo.domain.models.Type
import com.alavpa.kakebo.domain.usecases.GetAllLines
import com.alavpa.kakebo.domain.usecases.GetSavings
import com.alavpa.kakebo.domain.usecases.SetSavings
import com.alavpa.kakebo.presentation.mappers.LineUIMapper
import com.alavpa.kakebo.presentation.models.LineUI
import com.alavpa.kakebo.utils.AmountUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val getLines: GetAllLines,
    private val amountUtils: AmountUtils,
    private val setSavings: SetSavings,
    private val getSavings: GetSavings,
    private val linesUIMapper: LineUIMapper,
    initialState: StatisticsState
) : ViewModel(), StatisticsUserInteractions {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<StatisticsState>
        get() = _state

    override fun onInitializeOnce() {
        viewModelScope.launch {
            getLines().zip(getSavings()) { lines, savings ->
                Pair(lines, savings)
            }.collect { (lines, savings) ->
                val income = lines.filter { it.type == Type.Income }
                    .sumOf { it.amount }
                val outcome = lines.filter { it.type == Type.Outcome }
                    .sumOf { it.amount }
                val budget = income - outcome
                val budgetWithSavings = budget - savings
                _state.update { currentState ->
                    currentState.copy(
                        income = amountUtils.fromLongToCurrency(income),
                        outcome = amountUtils.fromLongToCurrency(outcome),
                        budget = budget,
                        budgetText = amountUtils.fromLongToCurrency(budget),
                        savings = String.format(Locale.ROOT, "%.2f", savings / 100.0),
                        savingsText = amountUtils.fromLongToCurrency(savings),
                        budgetWithSavings = amountUtils.fromLongToCurrency(budgetWithSavings),
                        lines = lines.map { linesUIMapper.from(it) }
                    )
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    override fun onSavingsChanged(value: String) {
        _state.update { currentState ->
            currentState.copy(savings = value)
        }
        viewModelScope.launch {
            flow {
                val longValue: Long = amountUtils.parseAmountToLong(value)
                setSavings(longValue)
                emit(longValue)
            }.debounce(500L).collect { longValue ->
                _state.update { currentState ->
                    val budgetWithSavings: Long = currentState.budget - longValue
                    currentState.copy(
                        savingsText = amountUtils.fromLongToCurrency(longValue),
                        budgetWithSavings = amountUtils.fromLongToCurrency(budgetWithSavings)
                    )
                }
            }
        }
    }
}

@Immutable
data class StatisticsState @Inject constructor(
    val income: String,
    val outcome: String,
    val budget: Long,
    val budgetText: String,
    val savings: String,
    val savingsText: String,
    val budgetWithSavings: String,
    val lines: List<LineUI>
) {
    companion object {
        val INITIAL = StatisticsState(
            income = "",
            outcome = "",
            budget = 0,
            budgetText = "",
            savings = "",
            savingsText = "",
            budgetWithSavings = "",
            lines = emptyList()
        )
    }
}

interface StatisticsUserInteractions {
    fun onInitializeOnce()
    fun onSavingsChanged(value: String)

    class Stub : StatisticsUserInteractions {
        override fun onInitializeOnce() = Unit
        override fun onSavingsChanged(value: String) = Unit
    }
}