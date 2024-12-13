package com.alavpa.kakebo.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.ParseException
import java.util.Locale
import javax.inject.Inject

class AmountUtils @Inject constructor() {

    fun parseAmountToLong(amount: String): Long {
        val doubleValue = try {
            DecimalFormat.getInstance(Locale.ROOT).parse(amount)?.toFloat() ?: 0f
        } catch (parseException: ParseException) {
            0f
        }
        return (doubleValue * 100f).toLong()
    }

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