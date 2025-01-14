package com.alavpa.kakebo.domain.usecases

import com.alavpa.kakebo.domain.KakeboRepository
import com.alavpa.kakebo.domain.models.Line
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class InsertNewLine @Inject constructor(private val repository: KakeboRepository) {
    operator fun invoke(line: Line): Flow<Result<Unit>> = repository.insertNewLine(line)
}
