package com.alavpa.kakebo.presentation.ui.statistics

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alavpa.kakebo.domain.models.Type
import com.alavpa.kakebo.domain.usecases.GetAllLines
import com.alavpa.kakebo.domain.usecases.GetSavings
import com.alavpa.kakebo.domain.usecases.RemoveLine
import com.alavpa.kakebo.domain.usecases.SetSavings
import com.alavpa.kakebo.presentation.mappers.LineUIMapper
import com.alavpa.kakebo.presentation.models.LineUI
import com.alavpa.kakebo.utils.AmountUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val DEBOUNCE_TIMEOUT = 500L

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val getLines: GetAllLines,
    private val amountUtils: AmountUtils,
    private val setSavings: SetSavings,
    private val getSavings: GetSavings,
    private val removeLine: RemoveLine,
    private val linesUIMapper: LineUIMapper,
    initialState: StatisticsState
) : ViewModel(), StatisticsUserInteractions {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<StatisticsState>
        get() = _state

    override fun onInitializeOnce() {
        viewModelScope.launch {
            getLines().combine(getSavings()) { resultLines, resultSavings ->
                val lines = resultLines.getOrThrow()
                val savings = resultSavings.getOrThrow()
                Result.success(Pair(lines, savings))
            }.catch {
                // Show error
            }.collect { result ->
                result.onSuccess { (lines, savings) ->
                    val income = lines.filter { it.type == Type.Income }.sumOf { it.amount }
                    val outcome = lines.filter { it.type == Type.Outcome }.sumOf { it.amount }
                    val budget = income - outcome
                    val longSavings = amountUtils.parseAmountToLong(savings)
                    val budgetWithSavings = budget - longSavings
                    _state.update { currentState ->
                        currentState.copy(
                            income = amountUtils.fromLongToCurrency(income),
                            outcome = amountUtils.fromLongToCurrency(outcome),
                            budget = budget,
                            budgetText = amountUtils.fromLongToCurrency(budget),
                            savings = savings,
                            savingsText = amountUtils.fromLongToCurrency(longSavings),
                            budgetWithSavings = amountUtils.fromLongToCurrency(budgetWithSavings),
                            lines = lines.map { linesUIMapper.from(it) }
                        )
                    }
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    override fun onSavingsChanged(value: String) {
        _state.update { currentState ->
            currentState.copy(savings = value)
        }
        setSavings(value).debounce(DEBOUNCE_TIMEOUT).launchIn(viewModelScope)
    }

    override fun onClickDeleteLine(id: Long) {
        _state.update {
            it.copy(lineToDelete = id)
        }
    }

    override fun onConfirmDelete() {
        _state.value.lineToDelete?.let {
            removeLine(it)
                .onEach {
                    _state.update {
                        it.copy(lineToDelete = null)
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    override fun onCancelDelete() {
        _state.update {
            it.copy(lineToDelete = null)
        }
    }
}

@Immutable
data class StatisticsState(
    val income: String,
    val outcome: String,
    val budget: Long,
    val budgetText: String,
    val savings: String,
    val savingsText: String,
    val budgetWithSavings: String,
    val lines: List<LineUI>,
    val lineToDelete: Long?
) {
    companion object {
        val INITIAL =
            StatisticsState(
                income = "",
                outcome = "",
                budget = 0,
                budgetText = "",
                savings = "",
                savingsText = "",
                budgetWithSavings = "",
                lines = emptyList(),
                lineToDelete = null
            )
    }
}

interface StatisticsUserInteractions {
    fun onInitializeOnce()
    fun onSavingsChanged(value: String)
    fun onClickDeleteLine(id: Long)
    fun onConfirmDelete()
    fun onCancelDelete()
    class Stub : StatisticsUserInteractions {
        override fun onInitializeOnce() = Unit
        override fun onSavingsChanged(value: String) = Unit
        override fun onClickDeleteLine(id: Long) = Unit
        override fun onConfirmDelete() = Unit
        override fun onCancelDelete() = Unit
    }
}
