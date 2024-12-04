package com.alavpa.kakebo.ui.outcome

import android.util.Log
import android.util.Pair
import androidx.lifecycle.ViewModel
import com.alavpa.kakebo.ui.components.PadUserInteractions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class OutcomePresenter @Inject constructor() : ViewModel(), OutcomeUserInteractions {

    private val _state = MutableStateFlow(OutcomeState.INITIAL)
    val state: StateFlow<OutcomeState>
        get() = _state

    override fun onMessageDismissed() {
        _state.update { currentState ->
            currentState.copy(showSuccess = false)
        }
    }

    override fun onClickNumber(number: String) {
        _state.update { currentState ->
            val currentText = "${currentState.currentText}$number"
            Log.d("onClickNumber", currentText)
            if (currentText.length > 9) {
                currentState
            } else {
                currentState.copy(
                    currentText = currentText,
                    formattedText = currentText.formatText()
                )
            }
        }
    }

    override fun onClickDelete() {
        _state.update { currentState ->
            if (currentState.currentText.isNotEmpty()) {
                val currentText = currentState.currentText.substring(
                    0,
                    currentState.currentText.lastIndex
                )
                currentState.copy(
                    currentText = currentText,
                    formattedText = currentText.formatText()
                )
            } else {
                currentState
            }
        }
    }

    override fun onClickOk() {
        _state.update { OutcomeState.INITIAL.copy(showSuccess = true) }
    }

    override fun onClickCategory(category: Pair<String, Boolean>) {
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

private fun String.formatText(): String {
    var currentText = this
    while (currentText.length < 4) {
        currentText = "0$currentText"
    }
    val decimalSeparator = DecimalFormatSymbols.getInstance(Locale.ROOT).decimalSeparator
    val currency = StringBuilder(currentText).apply {
        insert(currentText.length - 2, decimalSeparator)
    }.toString().toDouble()
    return NumberFormat.getCurrencyInstance().format(currency)
}

data class OutcomeState(
    val formattedText: String,
    val currentText: String,
    val description: String,
    val categories: List<Pair<String, Boolean>>,
    val showSuccess: Boolean,
    val isFixedOutcome: Boolean
) {
    companion object {
        val INITIAL = OutcomeState(
            formattedText = "000".formatText(),
            currentText = "",
            description = "",
            categories = listOf(
                Pair("Survival", false),
                Pair("Leisure", false),
                Pair("Culture", false),
                Pair("Extras", false)
            ),
            showSuccess = false,
            isFixedOutcome = false
        )
    }
}

interface OutcomeUserInteractions : PadUserInteractions {
    fun onMessageDismissed()
    fun onClickCategory(category: Pair<String, Boolean>)
    fun onDescriptionChanged(description: String)
    fun onIsFixedOutcomeChanged(value: Boolean)

    class Stub : OutcomeUserInteractions {
        override fun onMessageDismissed() = Unit
        override fun onClickNumber(number: String) = Unit
        override fun onClickDelete() = Unit
        override fun onClickOk() = Unit
        override fun onClickCategory(category: Pair<String, Boolean>) = Unit
        override fun onDescriptionChanged(description: String) = Unit
        override fun onIsFixedOutcomeChanged(value: Boolean) = Unit
    }
}