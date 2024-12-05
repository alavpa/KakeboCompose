package com.alavpa.kakebo.ui.outcome

import android.util.Pair
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alavpa.kakebo.domain.models.Line
import com.alavpa.kakebo.domain.models.Type
import com.alavpa.kakebo.domain.usecases.GetOutcomeLines
import com.alavpa.kakebo.domain.usecases.InsertNewLine
import com.alavpa.kakebo.ui.components.PadUserInteractions
import com.alavpa.kakebo.ui.mappers.CategoryUIMapper
import com.alavpa.kakebo.ui.mappers.LineUIMapper
import com.alavpa.kakebo.ui.models.CategoryUI
import com.alavpa.kakebo.ui.models.LineUI
import com.alavpa.kakebo.ui.utils.AmountUtils
import com.alavpa.kakebo.ui.utils.CalendarUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OutcomePresenter @Inject constructor(
    private val insertNewLine: InsertNewLine,
    private val getLines: GetOutcomeLines,
    private val calendarUtils: CalendarUtils,
    private val categoryUIMapper: CategoryUIMapper,
    private val lineUIMapper: LineUIMapper,
    private val amountUtils: AmountUtils
) : ViewModel(), OutcomeUserInteractions {

    private val _state = MutableStateFlow(OutcomeState.INITIAL)
    val state: StateFlow<OutcomeState>
        get() = _state

    init {
        viewModelScope.launch {
            getLines().collect { lines ->
                _state.update { currentState ->
                    currentState.copy(
                        lines = lines.map { lineUIMapper.from(it) },
                        formattedText = amountUtils.fromLongToCurrency(0)
                    )
                }
            }
        }
    }

    override fun onMessageDismissed() {
        _state.update { currentState ->
            currentState.copy(showSuccess = false)
        }
    }

    override fun onClickNumber(number: String) {
        _state.update { currentState ->
            val currentText = "${currentState.currentText}$number"
            if (currentText.length > 9) {
                currentState
            } else {
                currentState.copy(
                    currentText = currentText,
                    formattedText = amountUtils.fromTextToCurrency(currentText)
                )
            }
        }
    }

    override fun onClickDelete() {
        _state.update { currentState ->
            if (currentState.currentText.isNotEmpty()) {
                val currentText = currentState.currentText.substring(
                    startIndex = 0,
                    endIndex = currentState.currentText.lastIndex
                )
                currentState.copy(
                    currentText = currentText,
                    formattedText = amountUtils.fromTextToCurrency(currentText)
                )
            } else {
                currentState
            }
        }
    }

    override fun onClickOk() {
        viewModelScope.launch {
            with(_state.value) {
                val line = Line(
                    amount = currentText.toLongOrNull() ?: 0,
                    description = description,
                    timestamp = calendarUtils.getCurrentTimestamp(),
                    type = Type.Outcome,
                    category = categoryUIMapper.to(
                        categories.find { it.second }?.first ?: CategoryUI.Extras
                    ),
                    isFixed = isFixedOutcome
                )
                insertNewLine(line)
                _state.update { currentState ->
                    OutcomeState.INITIAL.copy(
                        showSuccess = true,
                        lines = currentState.lines,
                        formattedText = amountUtils.fromLongToCurrency(0)
                    )
                }
            }
        }
    }

    override fun onClickCategory(category: Pair<CategoryUI, Boolean>) {
        _state.update { currentState ->
            currentState.copy(
                categories = currentState.categories.map { currentCategory ->
                    if (category == currentCategory) Pair(
                        category.first,
                        !category.second
                    ) else Pair(currentCategory.first, false)
                }
            )
        }
    }

    override fun onDescriptionChanged(description: String) {
        _state.update { currentState ->
            currentState.copy(description = description)
        }
    }

    override fun onIsFixedOutcomeChanged(value: Boolean) {
        _state.update { currentState ->
            currentState.copy(isFixedOutcome = value)
        }
    }
}

data class OutcomeState(
    val lines: List<LineUI>,
    val formattedText: String,
    val currentText: String,
    val description: String,
    val categories: List<Pair<CategoryUI, Boolean>>,
    val showSuccess: Boolean,
    val isFixedOutcome: Boolean
) {
    companion object {
        val INITIAL = OutcomeState(
            lines = emptyList(),
            formattedText = "",
            currentText = "",
            description = "",
            categories = listOf(
                Pair(CategoryUI.Survival, false),
                Pair(CategoryUI.Leisure, false),
                Pair(CategoryUI.Culture, false),
                Pair(CategoryUI.Extras, false)
            ),
            showSuccess = false,
            isFixedOutcome = false
        )
    }
}

interface OutcomeUserInteractions : PadUserInteractions {
    fun onMessageDismissed()
    fun onClickCategory(category: Pair<CategoryUI, Boolean>)
    fun onDescriptionChanged(description: String)
    fun onIsFixedOutcomeChanged(value: Boolean)

    class Stub : OutcomeUserInteractions {
        override fun onMessageDismissed() = Unit
        override fun onClickNumber(number: String) = Unit
        override fun onClickDelete() = Unit
        override fun onClickOk() = Unit
        override fun onClickCategory(category: Pair<CategoryUI, Boolean>) = Unit
        override fun onDescriptionChanged(description: String) = Unit
        override fun onIsFixedOutcomeChanged(value: Boolean) = Unit
    }
}