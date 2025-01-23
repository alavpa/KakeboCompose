package com.alavpa.kakebo.presentation.ui.statistics

import androidx.compose.runtime.Immutable
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alavpa.kakebo.domain.models.Type
import com.alavpa.kakebo.domain.usecases.GetAllLines
import com.alavpa.kakebo.domain.usecases.GetSavings
import com.alavpa.kakebo.domain.usecases.RemoveLine
import com.alavpa.kakebo.domain.usecases.SetSavings
import com.alavpa.kakebo.presentation.mappers.LineUIMapper
import com.alavpa.kakebo.presentation.models.LineUI
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
                    val budgetWithSavings = budget - savings
                    _state.update { currentState ->
                        currentState.copy(
                            income = income.toString(),
                            outcome = outcome.toString(),
                            budget = budget,
                            budgetText = budget.toString(),
                            savings = savings.toString(),
                            budgetWithSavings = budgetWithSavings.toString(),
                            lines = lines.map { linesUIMapper.from(it) }
                        )
                    }
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    override fun onSavingsChanged(value: String) {
        if (value.isDigitsOnly()) {
            _state.update { currentState ->
                currentState.copy(savings = value)
            }
            val savings = value.toIntOrNull() ?: 0
            setSavings(savings).debounce(DEBOUNCE_TIMEOUT).launchIn(viewModelScope)
        }
    }

    override fun onClickDeleteLine(id: Long, isIncome: Boolean) {
        _state.update {
            it.copy(showDialogParams = ShowDialogParams(lineToDelete = id, isIncome = isIncome))
        }
    }

    override fun onConfirmDelete() {
        _state.value.showDialogParams?.let { params ->
            removeLine(params.lineToDelete)
                .onEach {
                    _state.update {
                        it.copy(showDialogParams = null)
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    override fun onCancelDelete() {
        _state.update {
            it.copy(showDialogParams = null)
        }
    }
}

@Immutable
data class StatisticsState(
    val income: String,
    val outcome: String,
    val budget: Int,
    val budgetText: String,
    val savings: String,
    val budgetWithSavings: String,
    val lines: List<LineUI>,
    val showDialogParams: ShowDialogParams?
) {
    companion object {
        val INITIAL =
            StatisticsState(
                income = "",
                outcome = "",
                budget = 0,
                budgetText = "",
                savings = "",
                budgetWithSavings = "",
                lines = emptyList(),
                showDialogParams = null
            )
    }
}

data class ShowDialogParams(
    val lineToDelete: Long,
    val isIncome: Boolean
)

interface StatisticsUserInteractions {
    fun onInitializeOnce()
    fun onSavingsChanged(value: String)
    fun onClickDeleteLine(id: Long, isIncome: Boolean)
    fun onConfirmDelete()
    fun onCancelDelete()
    class Stub : StatisticsUserInteractions {
        override fun onInitializeOnce() = Unit
        override fun onSavingsChanged(value: String) = Unit
        override fun onClickDeleteLine(id: Long, isIncome: Boolean) = Unit
        override fun onConfirmDelete() = Unit
        override fun onCancelDelete() = Unit
    }
}
