package com.alavpa.kakebo.domain.usecases

import com.alavpa.kakebo.domain.KakeboRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class RemoveLine @Inject constructor(private val repository: KakeboRepository) {
    operator fun invoke(id: Long): Flow<Result<Unit>> = repository.removeLine(id)
}
