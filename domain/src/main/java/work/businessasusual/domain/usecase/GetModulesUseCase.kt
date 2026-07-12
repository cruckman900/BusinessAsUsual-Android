package work.businessasusual.domain.usecase

import work.businessasusual.domain.model.Module
import work.businessasusual.domain.repository.ModuleRepository

class GetModulesUseCase(
    private val repository: ModuleRepository
) {
    suspend operator fun invoke(): List<Module> {
        return repository.getModules()
    }
}