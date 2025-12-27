package com.example.domain.usecase

import com.example.domain.model.HrAction
import com.example.domain.repository.HrRepository

class GetHrActionsUseCase(
    private val repository: HrRepository
) {
    suspend operator fun invoke(): List<HrAction> {
        return repository.getHrActions()
    }
}