package com.alavpa.kakebo.domain.usecases

import app.cash.turbine.test
import com.alavpa.kakebo.domain.models.Category
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlinx.coroutines.test.runTest

class GetCategoriesTest {
    private val useCase = GetCategories()

    @Test
    fun `when call use case being income should return income categories`() =
        runTest {
            val incomeCategories = listOf(Category.Salary, Category.Gifts, Category.Extras)

            useCase(isIncome = true).test {
                assertEquals(incomeCategories, awaitItem())
                awaitComplete()
            }
        }

    @Test
    fun `when call use case being outcome should return outcome categories`() =
        runTest {
            val outcomeCategories =
                listOf(
                    Category.Survival,
                    Category.Leisure,
                    Category.Culture,
                    Category.Extras
                )

            useCase(isIncome = false)
                .test {
                    assertEquals(outcomeCategories, awaitItem())
                    awaitComplete()
                }
        }
}
