package com.alavpa.kakebo.domain.usecases

import com.alavpa.kakebo.domain.models.Category
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetCategories @Inject constructor() {
    operator fun invoke(isIncome: Boolean): Flow<Result<List<Category>>> =
        flow {
            val categories = if (isIncome) {
                listOf(Category.Salary, Category.Gifts, Category.Extras)
            } else {
                listOf(Category.Survival, Category.Leisure, Category.Culture, Category.Extras)
            }
            emit(Result.success(categories))
        }.catch { emit(Result.failure(it)) }
}
