package com.alavpa.kakebo.ui.models

import androidx.annotation.StringRes
import com.alavpa.kakebo.R

enum class CategoryUI(@StringRes val resId: Int) {
    Survival(R.string.survival),
    Leisure(R.string.leisure),
    Culture(R.string.culture),
    Extras(R.string.extras),
    Salary(R.string.salary),
    Gifts(R.string.Gifts)
}