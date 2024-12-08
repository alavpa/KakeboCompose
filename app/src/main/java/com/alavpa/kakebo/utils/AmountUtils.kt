package com.alavpa.kakebo.utils

import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

class AmountUtils @Inject constructor() {

    fun fromTextToCurrency(amount: String): String {
        return amount.formatText()
    }

    fun fromLongToCurrency(amount: Long): String {
        return amount.toString().formatText()
    }

    fun reset(): String {
        return fromLongToCurrency(amount = 0)
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
}