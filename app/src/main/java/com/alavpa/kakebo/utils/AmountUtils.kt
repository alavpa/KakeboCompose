package com.alavpa.kakebo.utils

import androidx.annotation.VisibleForTesting
import java.text.DecimalFormat
import java.text.ParseException
import javax.inject.Inject

class AmountUtils @Inject constructor() {
    fun parseAmountToLong(amount: String): Long {
        val value = parse(amount)
        return (value * 100.0).toLong()
    }

    fun fromTextToCurrency(amount: String): String {
        return formatText(amount)
    }

    fun fromLongToCurrency(amount: Long): String {
        return formatText(amount.toString())
    }

    fun reset(): String {
        return fromLongToCurrency(amount = 0)
    }

    private fun formatText(text: String): String {
        val currency = (text.toLongOrNull() ?: 0) / 100.0
        return format(currency)
    }

    @VisibleForTesting
    fun format(currency: Double): String {
        val df = DecimalFormat.getCurrencyInstance()
        df.maximumFractionDigits = 2
        df.minimumFractionDigits = 2
        return df.format(currency)
    }

    @VisibleForTesting
    fun parse(value: String): Double {
        val df = DecimalFormat.getInstance()
        df.maximumFractionDigits = 2
        df.minimumFractionDigits = 2
        return try {
            df.parse(value)?.toDouble() ?: 0.0
        } catch (exception: ParseException) {
            0.0
        }
    }
}