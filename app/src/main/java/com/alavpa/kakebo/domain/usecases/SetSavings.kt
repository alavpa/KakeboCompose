package com.alavpa.kakebo.domain.usecases

import com.alavpa.kakebo.domain.KakeboRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SetSavings @Inject constructor(private val repository: KakeboRepository) {
    operator fun invoke(savings: String): Flow<Result<Unit>> = repository.setSavings(savings)
}
