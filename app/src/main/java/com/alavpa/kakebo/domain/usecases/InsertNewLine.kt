package com.alavpa.kakebo.domain.usecases

import com.alavpa.kakebo.domain.KakeboRepository
import com.alavpa.kakebo.domain.models.Line
import javax.inject.Inject

class InsertNewLine @Inject constructor(private val repository: KakeboRepository) {

    suspend operator fun invoke(line: Line) = repository.insertNewLine(line)
}