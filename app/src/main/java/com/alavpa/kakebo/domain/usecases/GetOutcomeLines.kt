package com.alavpa.kakebo.domain.usecases

import com.alavpa.kakebo.domain.KakeboRepository
import com.alavpa.kakebo.domain.models.Line
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOutcomeLines @Inject constructor(private val repository: KakeboRepository) {

    operator fun invoke(): Flow<List<Line>> = repository.getOutcomeLines()
}