package com.alavpa.kakebo.domain.usecases

import com.alavpa.kakebo.domain.models.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCategories @Inject constructor() {
    operator fun invoke(isIncome: Boolean): Flow<List<Category>> = flow {
        val categories = if (isIncome) {
            listOf(Category.Salary, Category.Gifts, Category.Extras)
        } else {
            listOf(Category.Survival, Category.Leisure, Category.Culture, Category.Extras)
        }
        emit(categories)
    }
}