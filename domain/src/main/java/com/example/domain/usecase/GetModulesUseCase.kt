package com.example.domain.usecase

import com.example.domain.model.Module
import com.example.domain.repository.ModuleRepository

class GetModulesUseCase(
    private val repository: ModuleRepository
) {
    suspend operator fun invoke(): List<Module> {
        return repository.getModules()
    }
}