package com.alavpa.kakebo.data.mappers

import com.alavpa.kakebo.data.model.TypeData
import com.alavpa.kakebo.domain.models.Type
import javax.inject.Inject

class TypeDataMapper @Inject constructor() {
    fun from(type: Type): TypeData = when (type) {
        Type.Income -> TypeData.Income
        Type.Outcome -> TypeData.Outcome
    }

    fun to(typeData: TypeData): Type = when (typeData) {
        TypeData.Income -> Type.Income
        TypeData.Outcome -> Type.Outcome
    }
}