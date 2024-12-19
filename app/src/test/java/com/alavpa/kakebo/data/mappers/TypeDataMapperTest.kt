package com.alavpa.kakebo.data.mappers

import com.alavpa.kakebo.data.model.TypeData
import com.alavpa.kakebo.domain.models.Type
import org.junit.Assert.assertEquals
import org.junit.Test

class TypeDataMapperTest {

    private val typeDataMapper = TypeDataMapper()

    @Test
    fun `when map from domain type should map properly`() {
        val type = Type.Income
        val expected = TypeData.Income

        val result = typeDataMapper.from(type)

        assertEquals(expected, result)
    }

    @Test
    fun `when map to domain type should map properly`() {
        val expected = Type.Income
        val typeData = TypeData.Income

        val result = typeDataMapper.to(typeData)

        assertEquals(expected, result)
    }
}
