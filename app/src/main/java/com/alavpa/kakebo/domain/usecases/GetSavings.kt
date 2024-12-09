package com.alavpa.kakebo.domain.usecases

import com.alavpa.kakebo.domain.KakeboRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavings @Inject constructor(private val repository: KakeboRepository) {

    operator fun invoke(): Flow<Long> = repository.getSavings()
}