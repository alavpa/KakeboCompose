package com.alavpa.kakebo.presentation.ui.lines

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alavpa.kakebo.domain.models.Line
import com.alavpa.kakebo.domain.models.Type
import com.alavpa.kakebo.domain.usecases.GetCategories
import com.alavpa.kakebo.domain.usecases.InsertNewLine
import com.alavpa.kakebo.presentation.mappers.CategoryUIMapper
import com.alavpa.kakebo.presentation.models.CategoryUI
import com.alavpa.kakebo.utils.CalendarUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LinesViewModel @Inject constructor(
    private val insertNewLine: InsertNewLine,
    private val getCategories: GetCategories,
    private val calendarUtils: CalendarUtils,
    private val categoryUIMapper: CategoryUIMapper,
    initialState: LinesState
) : ViewModel(), LinesUserInteractions {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<LinesState>
        get() = _state

    private val events = Channel<LinesEvent>(Channel.BUFFERED)
    val eventsFlow: Flow<LinesEvent>
        get() = events.receiveAsFlow()

    override fun onInitializeOnce(isIncome: Boolean) {
        viewModelScope.launch {
            getCategories(isIncome).collect { result ->
                result.onSuccess { categories ->
                    _state.update { currentState ->
                        currentState.copy(
                            categories = categories.map { category ->
                                categoryUIMapper.from(category)
                            }
                        )
                    }
                }
            }
        }
    }

    override fun onClickSend(isIncome: Boolean) {
        with(_state.value) {
            val line = Line(
                amount = (amountText).toIntOrNull() ?: 0,
                description = description,
                timestamp = calendarUtils.getCurrentTimestamp(),
                type = if (isIncome) Type.Income else Type.Outcome,
                category = categoryUIMapper.to(selectedCategory ?: CategoryUI.Extras),
                repeat = repeat
            )

            insertNewLine(line).onEach { result ->
                result.onSuccess {
                    _state.update { currentState ->
                        currentState.copy(amountText = "", description = "")
                    }
                    events.send(LinesEvent.ShowSuccessMessage)
                }
                result.onFailure {
                    Log.e(null, null, it)
                }
            }.launchIn(viewModelScope)
        }
    }

    override fun onAmountChanged(newAmount: String) {
        if (newAmount.isDigitsOnly()) {
            _state.update {
                it.copy(amountText = newAmount)
            }
        }
    }

    override fun onClickCategory(category: CategoryUI) {
        _state.update { currentState ->
            val selectedCategory = if (currentState.selectedCategory == category) null else category
            currentState.copy(selectedCategory = selectedCategory)
        }
    }

    override fun onDescriptionChanged(description: String) {
        _state.update { currentState ->
            currentState.copy(description = description)
        }
    }

    override fun onRepeatChanged(value: Boolean) {
        _state.update { currentState ->
            currentState.copy(repeat = value)
        }
    }
}

@Immutable
data class LinesState @Inject constructor(
    val amountText: String,
    val description: String,
    val categories: List<CategoryUI>,
    val selectedCategory: CategoryUI?,
    val repeat: Boolean
) {
    companion object {
        val INITIAL = LinesState(
            amountText = "",
            description = "",
            categories = emptyList(),
            selectedCategory = null,
            repeat = false
        )
    }
}

sealed class LinesEvent {
    data object ShowSuccessMessage : LinesEvent()
}

interface LinesUserInteractions {
    fun onClickCategory(category: CategoryUI)
    fun onDescriptionChanged(description: String)
    fun onRepeatChanged(value: Boolean)
    fun onInitializeOnce(isIncome: Boolean)
    fun onClickSend(isIncome: Boolean)
    fun onAmountChanged(newAmount: String)

    class Stub : LinesUserInteractions {
        override fun onClickCategory(category: CategoryUI) = Unit
        override fun onDescriptionChanged(description: String) = Unit
        override fun onRepeatChanged(value: Boolean) = Unit
        override fun onInitializeOnce(isIncome: Boolean) = Unit
        override fun onClickSend(isIncome: Boolean) = Unit
        override fun onAmountChanged(newAmount: String) = Unit
    }
}
