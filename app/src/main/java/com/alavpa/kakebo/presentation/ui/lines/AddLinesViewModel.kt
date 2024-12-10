package com.alavpa.kakebo.presentation.ui.lines

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alavpa.kakebo.domain.models.Line
import com.alavpa.kakebo.domain.models.Type
import com.alavpa.kakebo.domain.usecases.InsertNewLine
import com.alavpa.kakebo.presentation.components.PadUserInteractions
import com.alavpa.kakebo.presentation.components.SnackbarInteractions
import com.alavpa.kakebo.presentation.mappers.CategoryUIMapper
import com.alavpa.kakebo.presentation.models.CategoryUI
import com.alavpa.kakebo.utils.AmountUtils
import com.alavpa.kakebo.utils.CalendarUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddLinesViewModel @Inject constructor(
    private val insertNewLine: InsertNewLine,
    private val calendarUtils: CalendarUtils,
    private val categoryUIMapper: CategoryUIMapper,
    private val amountUtils: AmountUtils
) : ViewModel(), AddLinesUserInteractions {

    private val _state = MutableStateFlow(AddLinesState.INITIAL)
    val state: StateFlow<AddLinesState>
        get() = _state

    override fun onInitializeOnce(isIncome: Boolean) {
        viewModelScope.launch {
            val outcomeCategories = listOf(
                CategoryUI.Survival,
                CategoryUI.Leisure,
                CategoryUI.Culture,
                CategoryUI.Extras
            )
            val incomeCategories = listOf(
                CategoryUI.Salary,
                CategoryUI.Gifts,
                CategoryUI.Extras
            )
            _state.update { currentState ->
                currentState.copy(
                    formattedText = amountUtils.reset(),
                    categories = if (isIncome) incomeCategories else outcomeCategories
                )
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

    override fun onClickOk(isIncome: Boolean) {
        viewModelScope.launch {
            with(_state.value) {
                val line = Line(
                    amount = currentText.toLongOrNull() ?: 0,
                    description = description,
                    timestamp = calendarUtils.getCurrentTimestamp(),
                    type = if (isIncome) Type.Income else Type.Outcome,
                    category = categoryUIMapper.to(selectedCategory ?: CategoryUI.Extras),
                    isFixed = isFixedOutcome
                )
                insertNewLine(line)
                _state.update {
                    AddLinesState.INITIAL.copy(
                        showSuccess = true,
                        formattedText = amountUtils.reset()
                    )
                }
            }
        }
    }

    override fun onClickCategory(category: CategoryUI) {
        _state.update { currentState ->
            currentState.copy(
                selectedCategory = category
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

@Immutable
data class AddLinesState(
    val formattedText: String,
    val currentText: String,
    val description: String,
    val categories: List<CategoryUI>,
    val selectedCategory: CategoryUI?,
    val showSuccess: Boolean,
    val isFixedOutcome: Boolean
) {
    companion object {
        val INITIAL = AddLinesState(
            formattedText = "",
            currentText = "",
            description = "",
            categories = emptyList(),
            selectedCategory = null,
            showSuccess = false,
            isFixedOutcome = false
        )
    }
}

interface AddLinesUserInteractions : PadUserInteractions, SnackbarInteractions {
    fun onClickCategory(category: CategoryUI)
    fun onDescriptionChanged(description: String)
    fun onIsFixedOutcomeChanged(value: Boolean)
    fun onInitializeOnce(isIncome: Boolean)

    class Stub : AddLinesUserInteractions {
        override fun onMessageDismissed() = Unit
        override fun onClickNumber(number: String) = Unit
        override fun onClickDelete() = Unit
        override fun onClickOk(isIncome: Boolean) = Unit
        override fun onClickCategory(category: CategoryUI) = Unit
        override fun onDescriptionChanged(description: String) = Unit
        override fun onIsFixedOutcomeChanged(value: Boolean) = Unit
        override fun onInitializeOnce(isIncome: Boolean) = Unit
    }
}