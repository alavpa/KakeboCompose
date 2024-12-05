package com.alavpa.kakebo.ui.mappers

import com.alavpa.kakebo.domain.models.Category
import com.alavpa.kakebo.ui.models.CategoryUI
import javax.inject.Inject

class CategoryUIMapper @Inject constructor() {
    fun to(categoryUI: CategoryUI): Category = when (categoryUI) {
        CategoryUI.Survival -> Category.Survival
        CategoryUI.Leisure -> Category.Leisure
        CategoryUI.Culture -> Category.Culture
        CategoryUI.Extras -> Category.Extras
    }
}