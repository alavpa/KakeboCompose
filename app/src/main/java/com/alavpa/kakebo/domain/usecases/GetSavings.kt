package com.alavpa.kakebo.domain.usecases

import com.alavpa.kakebo.domain.KakeboRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetSavings @Inject constructor(private val repository: KakeboRepository) {
    operator fun invoke(): Flow<String> = repository.getSavings()
}
