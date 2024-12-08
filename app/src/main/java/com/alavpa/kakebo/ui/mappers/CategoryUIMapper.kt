package com.alavpa.kakebo.ui.mappers

import com.alavpa.kakebo.domain.models.Category
import com.alavpa.kakebo.ui.models.CategoryUI
import com.alavpa.kakebo.ui.models.CategoryUI.Culture
import com.alavpa.kakebo.ui.models.CategoryUI.Extras
import com.alavpa.kakebo.ui.models.CategoryUI.Gifts
import com.alavpa.kakebo.ui.models.CategoryUI.Leisure
import com.alavpa.kakebo.ui.models.CategoryUI.Salary
import com.alavpa.kakebo.ui.models.CategoryUI.Survival
import javax.inject.Inject

class CategoryUIMapper @Inject constructor() {
    fun to(categoryUI: CategoryUI): Category = when (categoryUI) {
        Survival -> Category.Survival
        Leisure -> Category.Leisure
        Culture -> Category.Culture
        Extras -> Category.Extras
        Salary -> Category.Salary
        Gifts -> Category.Gifts
    }
}