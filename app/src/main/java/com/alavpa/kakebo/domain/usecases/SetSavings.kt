package com.alavpa.kakebo.domain.usecases

import com.alavpa.kakebo.domain.KakeboRepository
import javax.inject.Inject

class SetSavings @Inject constructor(private val repository: KakeboRepository) {
    suspend operator fun invoke(savings: String) = repository.setSavings(savings)
}
