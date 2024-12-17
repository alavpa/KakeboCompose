package com.alavpa.kakebo.domain.usecases

import com.alavpa.kakebo.domain.KakeboRepository
import com.alavpa.kakebo.domain.models.Line
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetAllLines
@Inject
constructor(private val repository: KakeboRepository) {
    operator fun invoke(): Flow<List<Line>> = repository.getAllLines()
}
