package com.alavpa.kakebo.domain.usecases

import com.alavpa.kakebo.domain.KakeboRepository
import com.alavpa.kakebo.domain.models.Line
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllLines @Inject constructor(private val repository: KakeboRepository) {

    operator fun invoke(line: Line) = flow {
        emit(repository.getAllLines())
    }
}