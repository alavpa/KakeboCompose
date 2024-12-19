package com.alavpa.kakebo.data.mappers

import com.alavpa.kakebo.data.model.CategoryData
import com.alavpa.kakebo.domain.models.Category
import org.junit.Assert.assertEquals
import org.junit.Test

class CategoryDataMapperTest {

    private val categoryDataMapper = CategoryDataMapper()

    @Test
    fun `when map from domain category should map properly`() {
        val category = Category.Culture
        val expected = CategoryData.Culture

        val result = categoryDataMapper.from(category)

        assertEquals(expected, result)
    }

    @Test
    fun `when map to domain category should map properly`() {
        val categoryData = CategoryData.Culture
        val expected = Category.Culture

        val result = categoryDataMapper.to(categoryData)

        assertEquals(expected, result)
    }
}
