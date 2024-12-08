package com.alavpa.kakebo.data.mappers

import com.alavpa.kakebo.data.model.CategoryData
import com.alavpa.kakebo.domain.models.Category
import javax.inject.Inject

class CategoryDataMapper @Inject constructor() {
    fun from(category: Category): CategoryData = when (category) {
        Category.Survival -> CategoryData.Survival
        Category.Leisure -> CategoryData.Leisure
        Category.Culture -> CategoryData.Culture
        Category.Extras -> CategoryData.Extras
        Category.Salary -> CategoryData.Salary
        Category.Gifts -> CategoryData.Gifts
    }

    fun to(categoryData: CategoryData): Category = when (categoryData) {
        CategoryData.Survival -> Category.Survival
        CategoryData.Leisure -> Category.Leisure
        CategoryData.Culture -> Category.Culture
        CategoryData.Extras -> Category.Extras
        CategoryData.Salary -> Category.Salary
        CategoryData.Gifts -> Category.Gifts
    }
}